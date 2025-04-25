package com.xpert.service.impl;

import com.xpert.dto.location.CreateLocationRequestDTO;
import com.xpert.dto.location.LocationResponseDTO;
import com.xpert.entity.Location;
import com.xpert.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceImplTest {

    private LocationRepository locationRepository;
    private ModelMapper modelMapper;
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        modelMapper = new ModelMapper();
        locationService = new LocationServiceImpl(locationRepository, modelMapper);
    }

    @Test
    void createLocation_shouldSucceed() {
        CreateLocationRequestDTO dto = CreateLocationRequestDTO.builder()
                .title("Office")
                .country("Bangladesh")
                .city("Dhaka")
                .state("Dhaka Division")
                .zipCode("1000")
                .streetAddress("Main Street")
                .specialInstruction("Near the coffee shop")
                .build();

        Location entity = modelMapper.map(dto, Location.class);
        entity.setId(UUID.randomUUID());

        when(locationRepository.save(any(Location.class))).thenReturn(entity);

        LocationResponseDTO result = locationService.createLocation(dto);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Office");
        verify(locationRepository).save(any(Location.class));
    }


    @Test
    void getLocationById_shouldSucceed() {
        UUID id = UUID.randomUUID();
        Location location = new Location();
        location.setId(id);
        location.setTitle("Branch");

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        LocationResponseDTO result = locationService.getLocationById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo("Branch");
    }

    @Test
    void getLocationById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> locationService.getLocationById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Location not found with ID");
    }
}
