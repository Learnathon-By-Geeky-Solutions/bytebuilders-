package com.xpert.dto.order;

import com.xpert.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for representing order details in the booking module.
 * Used to return order data with full metadata including client, xpert, and timestamps.
 */
@Data
@Builder
public class OrderResponseDTO {

    /**
     * Unique identifier for the order.
     */
    @NotNull(message = "Order ID must not be null")
    private UUID id;

    /**
     * Associated agreement ID.
     */
    @NotNull(message = "Agreement ID must not be null")
    private UUID agreementId;

    /**
     * Full name of the client who booked the service.
     */
    @NotNull(message = "Client name must not be null")
    private String clientFullName;

    /**
     * Full name of the expert providing the service.
     */
    @NotNull(message = "Xpert name must not be null")
    private String xpertFullName;

    /**
     * Scheduled time for the service.
     */
    @NotNull(message = "Scheduled time must not be null")
    private Instant scheduledTime;

    /**
     * Actual start time of the service.
     */
    private Instant startedAt;

    /**
     * Actual completion time of the service.
     */
    private Instant completedAt;

    /**
     * Current status of the order.
     */
    @NotNull(message = "Order status must not be null")
    private OrderStatus status;

    /**
     * Timestamp when the order was created.
     */
    private Instant createdAt;

    /**
     * Timestamp when the order was last updated.
     */
    private Instant updatedAt;

    /**
     * Version field for optimistic locking (optional).
     */
    private Long version;
}
