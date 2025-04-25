package com.xpert.service;

import com.xpert.dto.invoice.CreateInvoiceRequestDTO;
import com.xpert.dto.invoice.InvoiceResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing invoices.
 */
public interface InvoiceService {

    /**
     * Creates a new invoice based on the provided data.
     *
     * @param dto the invoice creation request data
     * @return the created invoice response
     * @throws IllegalArgumentException if the order does not exist or is invalid
     */
    InvoiceResponseDTO createInvoice(CreateInvoiceRequestDTO dto);

    /**
     * Retrieves a single invoice by its ID.
     *
     * @param id the UUID of the invoice
     * @return the invoice response
     * @throws jakarta.persistence.EntityNotFoundException if no invoice is found
     */
    InvoiceResponseDTO getInvoiceById(UUID id);

    /**
     * Retrieves a paginated list of invoices.
     *
     * @param page the page index (zero-based)
     * @param size the number of invoices per page
     * @return a page of invoice responses
     */
    Page<InvoiceResponseDTO> getAllInvoices(int page, int size);
}
