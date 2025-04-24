package com.xpert.dto.workunit;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO for responding with Work Unit details.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkUnitResponseDTO {

    /**
     * Unique identifier of the work unit.
     */
    private UUID id;

    /**
     * Title of the work unit.
     */
    private String title;

    /**
     * Description of the work unit.
     */
    private String description;

    /**
     * Estimated time to complete the service.
     */
    private LocalTime estimatedTime;

    /**
     * Price of the service.
     */
    private BigDecimal price;

    /**
     * Whether the work unit is currently active.
     */
    private Boolean isActive;
} 