package com.xpert.service.impl;

import com.xpert.dto.invoice.CreateInvoiceRequestDTO;
import com.xpert.dto.invoice.InvoiceResponseDTO;
import com.xpert.entity.Invoice;
import com.xpert.entity.Order;
import com.xpert.enums.InvoiceStatus;
import com.xpert.enums.PaymentStatus;
import com.xpert.repository.InvoiceRepository;
import com.xpert.repository.OrderRepository;
import com.xpert.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of the InvoiceService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public InvoiceResponseDTO createInvoice(CreateInvoiceRequestDTO dto) {
        log.info("Creating invoice for order ID: {}", dto.getOrderId());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + dto.getOrderId()));

        Invoice invoice = Invoice.builder()
                .order(order)
                .referenceId(dto.getReferenceId())
                .description(dto.getDescription())
                .scheduledAt(dto.getScheduledAt())
                .completedAt(dto.getCompletedAt())
                .amount(dto.getAmount())
                .status(InvoiceStatus.GENERATED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Invoice saved = invoiceRepository.save(invoice);
        log.info("Invoice created with ID: {}", saved.getId());

        return modelMapper.map(saved, InvoiceResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceById(UUID id) {
        log.info("Fetching invoice with ID: {}", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));

        return modelMapper.map(invoice, InvoiceResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceResponseDTO> getAllInvoices(int page, int size) {
        log.info("Fetching all invoices with page={} and size={}", page, size);
        return invoiceRepository.findAll(PageRequest.of(page, size))
                .map(invoice -> modelMapper.map(invoice, InvoiceResponseDTO.class));
    }
}
