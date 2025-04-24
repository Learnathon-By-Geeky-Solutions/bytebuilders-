package com.xpert.dto.location;

import lombok.*;

import java.util.UUID;

/**
 * DTO for responding with Location details.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {

    /**
     * Unique identifier of the location.
     */
    private UUID id;

    /**
     * Display title for the location (e.g., "Home", "Office").
     */
    private String title;

    /**
     * Country where the service will be provided.
     */
    private String country;

    /**
     * State or region name.
     */
    private String state;

    /**
     * City name.
     */
    private String city;

    /**
     * Postal or ZIP code.
     */
    private String zipCode;

    /**
     * Full street address.
     */
    private String streetAddress;

    /**
     * Optional special instructions (e.g., floor number, landmarks).
     */
    private String specialInstruction;

    /**
     * Whether the location is active.
     */
    private Boolean isActive;
}

