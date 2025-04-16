package com.xpert.service.impl;

import com.xpert.dto.workunit.CreateWorkUnitRequestDTO;
import com.xpert.dto.workunit.WorkUnitResponseDTO;
import com.xpert.entity.WorkUnit;
import com.xpert.repository.WorkUnitRepository;
import com.xpert.service.WorkUnitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of {@link WorkUnitService} for managing work units (services).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WorkUnitServiceImpl implements WorkUnitService {

    private final WorkUnitRepository workUnitRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkUnitResponseDTO createWorkUnit(CreateWorkUnitRequestDTO dto) {
        log.info("Creating new work unit: {}", dto.getTitle());

        WorkUnit workUnit = modelMapper.map(dto, WorkUnit.class);
        workUnit.setIsActive(true); // Explicitly mark it active

        WorkUnit saved = workUnitRepository.save(workUnit);
        log.info("Work unit created with ID: {}", saved.getId());

        return modelMapper.map(saved, WorkUnitResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkUnitResponseDTO getWorkUnitById(UUID id) {
        log.info("Fetching work unit with ID: {}", id);
        WorkUnit workUnit = workUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work unit not found with ID: " + id));

        return modelMapper.map(workUnit, WorkUnitResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<WorkUnitResponseDTO> getAllWorkUnits(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }

        log.info("Fetching all work units - page: {}, size: {}", page, size);
        return workUnitRepository.findAll(PageRequest.of(page, size))
                .map(workUnit -> modelMapper.map(workUnit, WorkUnitResponseDTO.class));
    }
}
