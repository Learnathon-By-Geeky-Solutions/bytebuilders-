package com.xpert.dto.invoice;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for creating a new invoice.
 * Used to capture input data required to generate an invoice for an order.
 */
@Getter
@Setter
@ToString(exclude = {"referenceId"})
@EqualsAndHashCode
@NoArgsConstructor
public class CreateInvoiceRequestDTO {

    /**
     * The UUID of the order associated with this invoice.
     */
    @NotNull(message = "Order ID must not be null")
    private UUID orderId;

    /**
     * Optional reference ID (encrypted) for client or system.
     */
    @Size(max = 50, message = "Reference ID can be at most 50 characters")
    private String referenceId;

    /**
     * Description of the invoice or additional notes.
     */
    @Size(max = 2000, message = "Description can be up to 2000 characters")
    private String description;

    /**
     * Scheduled date and time for service execution.
     */
    @NotNull(message = "Scheduled time must not be null")
    private LocalDateTime scheduledAt;

    /**
     * Optional completed time if service is already completed.
     */
    private LocalDateTime completedAt;

    /**
     * Total amount to be billed in the invoice.
     */
    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid monetary value")
    private BigDecimal amount;
}
