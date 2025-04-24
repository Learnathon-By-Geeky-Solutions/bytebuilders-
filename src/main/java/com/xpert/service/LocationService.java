package com.xpert.service;

import com.xpert.dto.location.CreateLocationRequestDTO;
import com.xpert.dto.location.LocationResponseDTO;

import java.util.UUID;

/**
 * Service interface for managing location-related operations.
 */
public interface LocationService {

    /**
     * Creates a new location.
     *
     * @param dto the request DTO containing location details
     * @return the created location response
     * @throws IllegalArgumentException if the input data is invalid
     */
    LocationResponseDTO createLocation(CreateLocationRequestDTO dto);

    /**
     * Retrieves a location by its ID.
     *
     * @param id the unique identifier of the location
     * @return the location response DTO
     * @throws jakarta.persistence.EntityNotFoundException if no location exists for the given ID
     */
    LocationResponseDTO getLocationById(UUID id);
}
