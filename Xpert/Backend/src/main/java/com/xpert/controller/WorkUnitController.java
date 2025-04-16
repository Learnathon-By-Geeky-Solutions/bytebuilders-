package com.xpert.controller;

import com.xpert.dto.workunit.CreateWorkUnitRequestDTO;
import com.xpert.dto.workunit.WorkUnitResponseDTO;
import com.xpert.service.WorkUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing Work Units (expert services).
 */
@RestController
@RequestMapping("/api/work-units")
@RequiredArgsConstructor
@Slf4j
public class WorkUnitController {

    private final WorkUnitService workUnitService;

    /**
     * Creates a new work unit (service).
     *
     * @param dto the request payload
     * @return the created work unit response
     */
    @PostMapping
    public ResponseEntity<WorkUnitResponseDTO> createWorkUnit(
            @Valid @RequestBody CreateWorkUnitRequestDTO dto) {
        log.info("Received request to create work unit: {}", dto.getTitle());
        WorkUnitResponseDTO created = workUnitService.createWorkUnit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a work unit by ID.
     *
     * @param id UUID of the work unit
     * @return the work unit response
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkUnitResponseDTO> getWorkUnitById(@PathVariable UUID id) {
        log.info("Received request to fetch work unit with ID: {}", id);
        return ResponseEntity.ok(workUnitService.getWorkUnitById(id));
    }

    /**
     * Retrieves all work units with pagination.
     *
     * @param page page number (0-based)
     * @param size number of items per page
     * @return paginated list of work unit responses
     */
    @GetMapping
    public ResponseEntity<Page<WorkUnitResponseDTO>> getAllWorkUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to fetch all work units - page={}, size={}", page, size);
        return ResponseEntity.ok(workUnitService.getAllWorkUnits(page, size));
    }
}
