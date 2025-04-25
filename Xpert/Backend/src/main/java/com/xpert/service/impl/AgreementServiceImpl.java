package com.xpert.service.impl;

import com.xpert.dto.agreement.AgreementResponseDTO;
import com.xpert.dto.agreement.CreateAgreementRequestDTO;
import com.xpert.entity.*;
import com.xpert.enums.AgreementStatus;
import com.xpert.repository.*;
import com.xpert.service.AgreementService;
import jakarta.persistence.EntityExistsException;
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
 * Implementation of AgreementService for managing agreements between clients and experts.
 */
//annotation
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;
    private final WorkUnitRepository workUnitRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Override
    public AgreementResponseDTO createAgreement(CreateAgreementRequestDTO dto) {
        log.info("Creating agreement between client {} and expert {}", dto.getClientId(), dto.getXpertId());

        Users client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Users xpert = userRepository.findById(dto.getXpertId())
                .orElseThrow(() -> new EntityNotFoundException("Xpert not found"));

        WorkUnit workUnit = workUnitRepository.findById(dto.getWorkUnitId())
                .orElseThrow(() -> new EntityNotFoundException("Work unit not found"));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        if (agreementRepository.existsByClientAndXpertAndWorkUnit(client, xpert, workUnit)) {
            throw new EntityExistsException("Agreement already exists for the given client, expert, and work unit");
        }

        Agreement agreement = Agreement.builder()
                .client(client)
                .xpert(xpert)
                .workUnit(workUnit)
                .location(location)
                .status(AgreementStatus.PENDING)
                .type(dto.getType())
                .totalEstimatedTime(dto.getTotalEstimatedTime())
                .totalPrice(dto.getTotalPrice())
                .build();

        Agreement saved = agreementRepository.save(agreement);
        log.info("Agreement created with ID: {}", saved.getId());

        return modelMapper.map(saved, AgreementResponseDTO.class);
    }

    @Override
    public AgreementResponseDTO getAgreementById(UUID agreementId) {
        log.info("Fetching agreement with ID: {}", agreementId);
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new EntityNotFoundException("Agreement not found"));
        return modelMapper.map(agreement, AgreementResponseDTO.class);
    }

    @Override
    public Page<AgreementResponseDTO> getAllAgreements(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page index must be non-negative and size must be greater than 0");
        }

        log.info("Fetching all agreements with page={} and size={}", page, size);
        return agreementRepository.findAll(PageRequest.of(page, size))
                .map(agreement -> modelMapper.map(agreement, AgreementResponseDTO.class));
    }
}
