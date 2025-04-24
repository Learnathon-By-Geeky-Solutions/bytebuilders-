package com.xpert.controller;

import com.xpert.dto.location.LocationResponseDTO;
import com.xpert.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for handling location-related endpoints.
 */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Location", description = "APIs for managing location information")
public class LocationController {

    private final LocationService locationService;

    /**
     * Retrieves a location by its ID.
     *
     * @param id UUID of the location
     * @return LocationResponseDTO with location details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get location by ID", description = "Retrieve detailed information about a specific location by its UUID")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable @NotNull UUID id) {
        log.info("GET /api/locations/{}", id);
        return ResponseEntity.ok(locationService.getLocationById(id));
    }
}
