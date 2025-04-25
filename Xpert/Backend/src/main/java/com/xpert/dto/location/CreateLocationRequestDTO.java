package com.xpert.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for creating a new location entry for an agreement or user profile.
 * Represents the address where the service will be delivered.
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"streetAddress", "specialInstruction"})
public class CreateLocationRequestDTO {

    /**
     * Optional title to identify the location (e.g., "Home", "Office").
     */
    @Size(max = 255, message = "Title can have up to 255 characters")
    private String title;

    /**
     * Country where the service will take place.
     */
    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country can be up to 50 characters")
    private String country;

    /**
     * City of the location.
     */
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City can be up to 100 characters")
    private String city;

    /**
     * State or division of the location.
     */
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State can be up to 100 characters")
    private String state;

    /**
     * ZIP code or postal code of the location.
     */
    @NotBlank(message = "Zip code is required")
    @Size(max = 10, message = "Zip code can be up to 10 characters")
    private String zipCode;

    /**
     * Full street address or area description.
     */
    @NotBlank(message = "Street address is required")
    private String streetAddress;

    /**
     * Optional field for special instructions (e.g., "2nd floor, left side").
     */
    @Size(max = 2000, message = "Special instruction can be up to 2000 characters")
    private String specialInstruction;
}
