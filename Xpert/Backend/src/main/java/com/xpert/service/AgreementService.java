package com.xpert.service;

import com.xpert.dto.agreement.AgreementResponseDTO;
import com.xpert.dto.agreement.CreateAgreementRequestDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing agreements between clients and experts.
 * Provides operations to create and retrieve agreement information.
 */
public interface AgreementService {

    /**
     * Creates a new agreement between a client and an expert.
     *
     * @param dto the request data containing agreement details
     * @return the created agreement as a response DTO
     *  @throws IllegalArgumentException if agreement data is invalid
     *  @throws EntityExistsException if agreement already exists
     */
    AgreementResponseDTO createAgreement(CreateAgreementRequestDTO dto);

    /**
     * Retrieves a specific agreement by its ID.
     *
     * @param agreementId the unique identifier of the agreement
     * @return the corresponding agreement response DTO
     * @throws EntityNotFoundException if no agreement exists with the given ID
     */
    AgreementResponseDTO getAgreementById(UUID agreementId);

    /**
     * Retrieves all agreements with pagination.
     *
     * @param page the page number (0-based)
     * @param size the number of items per page
     * @return a paginated list of agreement response DTOs
     */
    Page<AgreementResponseDTO> getAllAgreements(int page, int size);
}
