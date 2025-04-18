package com.xpert.service;

import com.xpert.dto.payment.CreatePaymentRequestDTO;
import com.xpert.dto.payment.PaymentResponseDTO;

import java.util.UUID;

/**
 * Service interface for handling payment-related operations.
 */
public interface PaymentService {

    /**
     * Creates a new payment record.
     *
     * @param dto the payment request details
     * @return the created payment response
     * @throws jakarta.persistence.EntityNotFoundException if order or payment method is not found
     * @throws IllegalArgumentException if payment data is invalid or duplicate
     */
    PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto);

    /**
     * Retrieves a payment by its ID.
     *
     * @param id UUID of the payment
     * @return the payment response
     * @throws jakarta.persistence.EntityNotFoundException if no payment is found with the given ID
     */
    PaymentResponseDTO getPaymentById(UUID id);
}
