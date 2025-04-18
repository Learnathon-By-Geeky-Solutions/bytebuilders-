package com.xpert.dto.payment;

import com.xpert.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for responding with payment details.
 */
@Getter
@Setter
@ToString(exclude = {"transactionId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    /**
     * Unique ID of the payment.
     */
    private UUID id;

    /**
     * Associated order ID.
     */
    private UUID orderId;

    /**
     * ID of the payment method used.
     */
    private Integer paymentMethodId;

    /**
     * Paid amount.
     */
    private BigDecimal amount;

    /**
     * Status of the payment (e.g., PENDING, COMPLETED).
     */
    private PaymentStatus status;

    /**
     * Encrypted transaction ID.
     */
    private String transactionId;

    /**
     * Time when payment was completed (nullable).
     */
    private Instant paidAt;

    /**
     * Creation timestamp.
     */
    private Instant createdAt;

    /**
     * Last update timestamp.
     */
    private Instant updatedAt;
}
