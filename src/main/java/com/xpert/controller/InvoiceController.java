package com.xpert.controller;

import com.xpert.dto.invoice.CreateInvoiceRequestDTO;
import com.xpert.dto.invoice.InvoiceResponseDTO;
import com.xpert.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing invoices.
 */
@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Invoice", description = "APIs for managing invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    /**
     * Creates a new invoice.
     *
     * @param dto the invoice creation request payload
     * @return the created invoice
     */
    @PostMapping
    @Operation(summary = "Create invoice", description = "Create a new invoice associated with an order")
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody CreateInvoiceRequestDTO dto) {
        log.info("POST /api/invoices → Creating invoice for orderId: {}", dto.getOrderId());
        InvoiceResponseDTO response = invoiceService.createInvoice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id the UUID of the invoice
     * @return the invoice details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID", description = "Fetch a specific invoice by its UUID")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable UUID id) {
        log.info("GET /api/invoices/{}", id);
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    /**
     * Retrieves all invoices with pagination.
     *
     * @param page page number
     * @param size page size
     * @return paginated list of invoices
     */
    @GetMapping
    @Operation(summary = "Get all invoices", description = "Fetch all invoices with pagination support")
    public ResponseEntity<Page<InvoiceResponseDTO>> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /api/invoices?page={}&size={}", page, size);
        return ResponseEntity.ok(invoiceService.getAllInvoices(page, size));
    }
}
