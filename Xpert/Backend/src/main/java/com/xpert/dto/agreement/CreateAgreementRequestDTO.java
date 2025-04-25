package com.xpert.dto.agreement;

import com.xpert.enums.AgreementType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO for creating a new agreement between a client and an expert.
 */
@Data
@Builder
public class CreateAgreementRequestDTO {

    /**
     * UUID of the client (customer).
     */
    @NotNull(message = "Client ID is required")
    private UUID clientId;

    /**
     * UUID of the xpert (expert user).
     */
    @NotNull(message = "Xpert ID is required")
    private UUID xpertId;

    /**
     * UUID of the work unit.
     */
    @NotNull(message = "Work Unit ID is required")
    private UUID workUnitId;

    /**
     * UUID of the location where the service will be delivered.
     */
    @NotNull(message = "Location ID is required")
    private UUID locationId;

    /**
     * Type of agreement (e.g., ONLINE, OFFLINE).
     */
    @NotNull(message = "Agreement type is required")
    private AgreementType type;

    /**
     * Estimated time to complete the service (hh:mm:ss).
     */
    @NotNull(message = "Total estimated time is required")
    @AssertTrue(message = "Total estimated time must be positive and not exceed 24 hours")
    private boolean isValidTotalEstimatedTime() {
        return totalEstimatedTime != null &&
               !totalEstimatedTime.equals(LocalTime.MIDNIGHT) &&
               totalEstimatedTime.compareTo(LocalTime.of(24, 0)) < 0;
    }

    private LocalTime totalEstimatedTime;


    /**
     * Total agreed price for the service.
     */
    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Price can have up to 10 digits and 2 decimal places")
    private BigDecimal totalPrice;
}
