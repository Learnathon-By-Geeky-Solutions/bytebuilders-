package com.xpert.dto.payment;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for creating a new payment record.
 */
@Getter
@Setter
@ToString(exclude = {"transactionId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequestDTO {

    /**
     * UUID of the order associated with the payment.
     */
    @NotNull(message = "Order ID must not be null")
    private UUID orderId;

    /**
     * ID of the payment method used for the payment.
     */
    @NotNull(message = "Payment method ID must not be null")
    private Integer paymentMethodId;

    /**
     * Amount paid.
     */
    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid monetary value")
    private BigDecimal amount;

    /**
     * Encrypted transaction identifier (e.g., from payment gateway).
     */
    @Size(max = 50, message = "Transaction ID must be at most 50 characters")
    private String transactionId;
}
