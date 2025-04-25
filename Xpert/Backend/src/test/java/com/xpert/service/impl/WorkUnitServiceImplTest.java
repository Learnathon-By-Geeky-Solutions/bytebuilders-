package com.xpert.service.impl;

import com.xpert.dto.workunit.CreateWorkUnitRequestDTO;
import com.xpert.dto.workunit.WorkUnitResponseDTO;
import com.xpert.entity.WorkUnit;
import com.xpert.repository.WorkUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkUnitServiceImplTest {

    private WorkUnitRepository workUnitRepository;
    private ModelMapper modelMapper;
    private WorkUnitServiceImpl workUnitService;

    private final UUID workUnitId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        workUnitRepository = mock(WorkUnitRepository.class);
        modelMapper = new ModelMapper();
        workUnitService = new WorkUnitServiceImpl(workUnitRepository, modelMapper);
    }

    @Test
    void createWorkUnit_shouldSucceed() {
        CreateWorkUnitRequestDTO dto = CreateWorkUnitRequestDTO.builder()
                .title("Test Service")
                .description("Test Description")
                .price(BigDecimal.valueOf(150.0)) 
                .estimatedTime(LocalTime.of(1, 30)) 
                .build();

        WorkUnit workUnit = modelMapper.map(dto, WorkUnit.class);
        workUnit.setId(workUnitId);
        workUnit.setIsActive(true);

        when(workUnitRepository.save(any(WorkUnit.class))).thenReturn(workUnit);

        WorkUnitResponseDTO result = workUnitService.createWorkUnit(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(workUnitId);
    }

    @Test
    void getWorkUnitById_shouldReturnWorkUnit() {
        WorkUnit workUnit = new WorkUnit();
        workUnit.setId(workUnitId);

        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.of(workUnit));

        WorkUnitResponseDTO result = workUnitService.getWorkUnitById(workUnitId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(workUnitId);
    }

    @Test
    void getWorkUnitById_shouldThrowIfNotFound() {
        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workUnitService.getWorkUnitById(workUnitId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Work unit not found");
    }

    @Test
    void getAllWorkUnits_shouldReturnPage() {
        Page<WorkUnit> page = new PageImpl<>(Collections.singletonList(new WorkUnit()));
        when(workUnitRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<WorkUnitResponseDTO> result = workUnitService.getAllWorkUnits(0, 5);
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getAllWorkUnits_shouldThrowIfPageOrSizeInvalid() {
        assertThatThrownBy(() -> workUnitService.getAllWorkUnits(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Page must be >= 0 and size must be > 0");
    }
}
