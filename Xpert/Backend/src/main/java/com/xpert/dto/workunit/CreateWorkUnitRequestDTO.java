package com.xpert.dto.workunit;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * DTO for creating a new Work Unit, representing a service offered by an expert.
 */
@Getter
@Setter
@ToString
public class CreateWorkUnitRequestDTO {

    /**
     * Title of the work unit (e.g., "AC Installation").
     */
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    /**
     * Description of the work unit.
     */
    @Size(max = 2000, message = "Description can be up to 2000 characters")
    private String description;

    /**
     * Estimated time to complete the service (hh:mm:ss).
     */
    @NotNull(message = "Estimated time is required")
    @AssertTrue(message = "Estimated time must be positive and under 24 hours")
    private boolean isValidEstimatedTime() {
        return estimatedTime != null &&
               !estimatedTime.equals(LocalTime.MIDNIGHT) &&
               estimatedTime.compareTo(LocalTime.of(24, 0)) < 0;
    }

    private LocalTime estimatedTime;

    /**
     * Price of the service.
     */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount with 2 decimal places")
    private BigDecimal price;
}
