package com.xpert.service.impl;

import com.xpert.dto.location.CreateLocationRequestDTO;
import com.xpert.dto.location.LocationResponseDTO;
import com.xpert.entity.Location;
import com.xpert.repository.LocationRepository;
import com.xpert.service.LocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for handling Location operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationResponseDTO createLocation(CreateLocationRequestDTO dto) {
        log.info("Creating new location with title: {}", dto.getTitle());

        Location location = modelMapper.map(dto, Location.class);
        Location savedLocation = locationRepository.save(location);

        log.info("Location created with ID: {}", savedLocation.getId());
        return modelMapper.map(savedLocation, LocationResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public LocationResponseDTO getLocationById(UUID id) {
        log.info("Fetching location with ID: {}", id);

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));

        return modelMapper.map(location, LocationResponseDTO.class);
    }

}
