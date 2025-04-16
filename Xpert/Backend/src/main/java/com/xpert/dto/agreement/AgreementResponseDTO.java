package com.xpert.dto.agreement;

import com.xpert.enums.AgreementStatus;
import com.xpert.enums.AgreementType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO for returning full agreement details to the client.
 */
@Getter
@Setter
@ToString
public class AgreementResponseDTO {

    /**
     * UUID of the agreement.
     */
    private UUID id;

    /**
     * UUID of the client.
     */
    private UUID clientId;

    /**
     * Full name of the client.
     */
    private String clientFullName;

    /**
     * UUID of the xpert.
     */
    private UUID xpertId;

    /**
     * Full name of the expert.
     */
    private String xpertFullName;

    /**
     * UUID of the work unit.
     */
    private UUID workUnitId;

    /**
     * Title of the work unit.
     */
    private String workUnitTitle;

    /**
     * UUID of the location.
     */
    private UUID locationId;

    /**
     * Title of the location.
     */
    private String locationTitle;

    /**
     * Type of agreement (ONLINE/OFFLINE).
     */
    private AgreementType type;

    /**
     * Estimated time to complete the service.
     */
    private LocalTime totalEstimatedTime;

    /**
     * Agreed total price for the service.
     */
    private BigDecimal totalPrice;

    /**
     * Status of the agreement.
     */
    private AgreementStatus status;

    /**
     * Timestamp of agreement creation.
     */
    private Instant createdAt;

    /**
     * Timestamp of the last update to the agreement.
     */
    private Instant updatedAt;
}
