package com.xpert.dto.invoice;

import com.xpert.enums.InvoiceStatus;
import com.xpert.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for responding with invoice details.
 * Used to send invoice information to clients or internal systems.
 */
@Getter
@Setter
@ToString(exclude = {"referenceId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDTO {

    /**
     * Unique identifier of the invoice.
     */
    private UUID id;

    /**
     * UUID of the associated order.
     */
    private UUID orderId;

    /**
     * Encrypted reference ID.
     */
    private String referenceId;

    /**
     * Optional description or notes.
     */
    private String description;

    /**
     * When the service is scheduled.
     */
    private LocalDateTime scheduledAt;

    /**
     * When the service was completed (if applicable).
     */
    private LocalDateTime completedAt;

    /**
     * Current invoice status.
     */
    private InvoiceStatus status;

    /**
     * Total billed amount.
     */
    private BigDecimal amount;

    /**
     * Status of the payment.
     */
    private PaymentStatus paymentStatus;

    /**
     * When the invoice was issued.
     */
    private Instant issuedAt;

    /**
     * Timestamp of invoice creation.
     */
    private Instant createdAt;

    /**
     * Timestamp of last update.
     */
    private Instant updatedAt;
}
