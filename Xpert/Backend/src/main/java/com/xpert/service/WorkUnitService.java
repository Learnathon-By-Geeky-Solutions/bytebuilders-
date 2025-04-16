package com.xpert.service;

import com.xpert.dto.workunit.CreateWorkUnitRequestDTO;
import com.xpert.dto.workunit.WorkUnitResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing Work Units representing services offered by experts.
 */
public interface WorkUnitService {

    /**
     * Creates a new Work Unit.
     *
     * @param dto the request DTO containing the work unit details
     * @return the created work unit as a response DTO
     * @throws IllegalArgumentException if input validation fails
     */
    WorkUnitResponseDTO createWorkUnit(CreateWorkUnitRequestDTO dto);

    /**
     * Retrieves a work unit by its unique identifier.
     *
     * @param id the UUID of the work unit
     * @return the work unit response DTO
     * @throws jakarta.persistence.EntityNotFoundException if no work unit found with the given ID
     */
    WorkUnitResponseDTO getWorkUnitById(UUID id);

    /**
     * Retrieves a paginated list of all work units.
     *
     * @param page the page number (0-based)
     * @param size the number of items per page
     * @return a paginated list of work unit response DTOs
     */
    Page<WorkUnitResponseDTO> getAllWorkUnits(int page, int size);
}
