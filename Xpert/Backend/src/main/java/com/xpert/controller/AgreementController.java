package com.xpert.controller;

import com.xpert.dto.agreement.AgreementResponseDTO;
import com.xpert.dto.agreement.CreateAgreementRequestDTO;
import com.xpert.service.AgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing service agreements between clients and experts.
 */
@RestController
@RequestMapping("/api/agreements")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Agreement Controller", description = "Handles agreement creation and retrieval")
public class AgreementController {

    private final AgreementService agreementService;

    /**
     * Creates a new agreement between a client and an expert.
     *
     * @param dto the agreement creation request
     * @return the created agreement
     */
    @PostMapping
    @Operation(summary = "Create a new agreement")
    public ResponseEntity<AgreementResponseDTO> createAgreement(@Valid @RequestBody CreateAgreementRequestDTO dto) {
        log.info("Received request to create agreement between client {} and xpert {}", dto.getClientId(), dto.getXpertId());
        AgreementResponseDTO response = agreementService.createAgreement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a specific agreement by its ID.
     *
     * @param agreementId the ID of the agreement
     * @return the agreement details
     */
    @GetMapping("/{agreementId}")
    @Operation(summary = "Get agreement by ID")
    public ResponseEntity<AgreementResponseDTO> getAgreementById(@PathVariable UUID agreementId) {
        log.info("Received request to fetch agreement with ID: {}", agreementId);
        AgreementResponseDTO agreement = agreementService.getAgreementById(agreementId);
        return ResponseEntity.ok(agreement);
    }

    /**
     * Retrieves all agreements with pagination.
     *
     * @param page the page number (0-based)
     * @param size the number of items per page
     * @return a paginated list of agreements
     */
    @GetMapping
    @Operation(summary = "Get all agreements with pagination")
    public ResponseEntity<Page<AgreementResponseDTO>> getAllAgreements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to fetch agreements - page={}, size={}", page, size);
        Page<AgreementResponseDTO> agreements = agreementService.getAllAgreements(page, size);
        return ResponseEntity.ok(agreements);
    }
}
