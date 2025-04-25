package com.xpert.dto.order;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for creating a new order request.
 * <p>
 * Contains the necessary information to initiate an order in the booking module.
 */
@Data
@Builder
public class CreateOrderRequestDTO {

    /**
     * The ID of the agreement associated with this order.
     */
    @NotNull(message = "Agreement ID must not be null")
    private UUID agreementId;

    /**
     * The ID of the client making the order.
     */
    @NotNull(message = "Client ID must not be null")
    private UUID clientId;

    /**
     * The ID of the expert providing the service.
     */
    @NotNull(message = "Xpert ID must not be null")
    private UUID xpertId;

    /**
     * The scheduled time when the service should be provided.
     */
    @NotNull(message = "Scheduled time must not be null")
    @Future(message = "Scheduled time must be in the future")
    private Instant scheduledTime;
}
