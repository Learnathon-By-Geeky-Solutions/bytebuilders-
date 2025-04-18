package com.xpert.service.impl;

import com.xpert.dto.payment.CreatePaymentRequestDTO;
import com.xpert.dto.payment.PaymentResponseDTO;
import com.xpert.entity.Order;
import com.xpert.entity.Payment;
import com.xpert.entity.PaymentMethod;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.PaymentMethodRepository;
import com.xpert.repository.PaymentRepository;
import com.xpert.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Service implementation for payment-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto) {
        log.info("Creating payment for orderId={}, methodId={}, amount={}",
                dto.getOrderId(), dto.getPaymentMethodId(), dto.getAmount());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + dto.getOrderId()));

        PaymentMethod method = paymentMethodRepository.findById(dto.getPaymentMethodId())
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with ID: " + dto.getPaymentMethodId()));

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(method)
                .amount(dto.getAmount())
                .transactionId(dto.getTransactionId())
                .paidAt(Instant.now())
                .build();

        try {
            Payment saved = paymentRepository.save(payment);
            return modelMapper.map(saved, PaymentResponseDTO.class);
        } catch (Exception ex) {
            log.error("Payment creation failed", ex);
            throw new IllegalArgumentException("Unable to process payment request");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(UUID id) {
        log.info("Fetching payment with ID: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
}
