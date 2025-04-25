package com.xpert.service.impl;

import com.xpert.dto.payment.CreatePaymentRequestDTO;
import com.xpert.dto.payment.PaymentResponseDTO;
import com.xpert.entity.Order;
import com.xpert.entity.Payment;
import com.xpert.entity.PaymentMethod;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.PaymentMethodRepository;
import com.xpert.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentRepository paymentRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentMethodRepository = mock(PaymentMethodRepository.class);
        orderRepository = mock(OrderRepository.class);
        modelMapper = new ModelMapper();
        paymentService = new PaymentServiceImpl(paymentRepository, paymentMethodRepository, orderRepository, modelMapper);
    }

    @Test
    void createPayment_shouldSucceed() {
        UUID orderId = UUID.randomUUID();
        UUID methodId = UUID.randomUUID();
        CreatePaymentRequestDTO dto = CreatePaymentRequestDTO.builder()
                .orderId(orderId)
                .paymentMethodId(methodId)
                .transactionId("TX12345")
                .amount(new BigDecimal("100.50"))
                .build();

        Order order = new Order();
        PaymentMethod method = new PaymentMethod();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(paymentMethodRepository.findById(methodId)).thenReturn(Optional.of(method));

        Payment saved = Payment.builder()
                .id(UUID.randomUUID())
                .order(order)
                .paymentMethod(method)
                .transactionId("TX12345")
                .amount(new BigDecimal("100.50"))
                .paidAt(Instant.now())
                .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        PaymentResponseDTO result = paymentService.createPayment(dto);

        assertThat(result).isNotNull();
        verify(orderRepository).findById(orderId);
        verify(paymentMethodRepository).findById(methodId);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void createPayment_shouldThrowIfOrderNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID methodId = UUID.randomUUID();
        CreatePaymentRequestDTO dto = CreatePaymentRequestDTO.builder()
                .orderId(orderId)
                .paymentMethodId(methodId)
                .amount(new BigDecimal("100"))
                .transactionId("TX123")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.createPayment(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    void createPayment_shouldThrowIfPaymentMethodNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID methodId = UUID.randomUUID();
        CreatePaymentRequestDTO dto = CreatePaymentRequestDTO.builder()
                .orderId(orderId)
                .paymentMethodId(methodId)
                .amount(new BigDecimal("100"))
                .transactionId("TX123")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(paymentMethodRepository.findById(methodId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.createPayment(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Payment method not found");
    }

    @Test
    void createPayment_shouldThrowIfSaveFails() {
        UUID orderId = UUID.randomUUID();
        UUID methodId = UUID.randomUUID();
        CreatePaymentRequestDTO dto = CreatePaymentRequestDTO.builder()
                .orderId(orderId)
                .paymentMethodId(methodId)
                .transactionId("TX_ERR")
                .amount(new BigDecimal("250.00"))
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(paymentMethodRepository.findById(methodId)).thenReturn(Optional.of(new PaymentMethod()));
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> paymentService.createPayment(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unable to process payment");
    }

    @Test
    void getPaymentById_shouldReturnPayment() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment();
        payment.setId(paymentId);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        PaymentResponseDTO result = paymentService.getPaymentById(paymentId);
        assertThat(result).isNotNull();
    }

    @Test
    void getPaymentById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.getPaymentById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Payment not found");
    }
}
