package com.xpert.service.impl;

import com.xpert.dto.invoice.CreateInvoiceRequestDTO;
import com.xpert.dto.invoice.InvoiceResponseDTO;
import com.xpert.entity.Invoice;
import com.xpert.entity.Order;
import com.xpert.enums.InvoiceStatus;
import com.xpert.enums.PaymentStatus;
import com.xpert.repository.InvoiceRepository;
import com.xpert.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;


import java.math.BigDecimal;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    private InvoiceRepository invoiceRepository;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        orderRepository = mock(OrderRepository.class);
        modelMapper = new ModelMapper();
        invoiceService = new InvoiceServiceImpl(invoiceRepository, orderRepository, modelMapper);
    }

    @Test
    void createInvoice_shouldSucceed() {
        UUID orderId = UUID.randomUUID();
        CreateInvoiceRequestDTO dto = CreateInvoiceRequestDTO.builder()
                .orderId(orderId)
                .referenceId("ref123")
                .description("Test invoice")
                .scheduledAt(LocalDateTime.now())
                .completedAt(LocalDateTime.now())

                .amount(BigDecimal.valueOf(999.99))
                .build();

        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Invoice savedInvoice = Invoice.builder()
                .id(UUID.randomUUID())
                .order(order)
                .referenceId(dto.getReferenceId())
                .description(dto.getDescription())
                .scheduledAt(dto.getScheduledAt())
                .completedAt(dto.getCompletedAt())
                .amount(dto.getAmount())
                .status(InvoiceStatus.GENERATED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);

        InvoiceResponseDTO result = invoiceService.createInvoice(dto);

        assertThat(result).isNotNull();
        verify(orderRepository).findById(orderId);
        verify(invoiceRepository).save(any(Invoice.class));
    }

    @Test
    void createInvoice_shouldThrowIfOrderNotFound() {
        UUID missingOrderId = UUID.randomUUID();

        CreateInvoiceRequestDTO dto = CreateInvoiceRequestDTO.builder()
                .orderId(missingOrderId)
                .referenceId("ref123")
                .amount(BigDecimal.TEN)
                .build();

        when(orderRepository.findById(missingOrderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> invoiceService.createInvoice(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    void getInvoiceById_shouldSucceed() {
        UUID invoiceId = UUID.randomUUID();
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        InvoiceResponseDTO result = invoiceService.getInvoiceById(invoiceId);

        assertThat(result).isNotNull();
        verify(invoiceRepository).findById(invoiceId);
    }

    @Test
    void getInvoiceById_shouldThrowIfNotFound() {
        UUID missingId = UUID.randomUUID();
        when(invoiceRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> invoiceService.getInvoiceById(missingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Invoice not found");
    }

    @Test
    void getAllInvoices_shouldReturnPagedResult() {
        Invoice invoice = new Invoice();
        Page<Invoice> page = new PageImpl<>(Collections.singletonList(invoice));

        when(invoiceRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<InvoiceResponseDTO> result = invoiceService.getAllInvoices(0, 10);

        assertThat(result.getContent()).hasSize(1);
        verify(invoiceRepository).findAll(PageRequest.of(0, 10));
    }
}
