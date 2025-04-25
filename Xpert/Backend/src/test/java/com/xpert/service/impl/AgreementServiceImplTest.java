package com.xpert.service.impl;

import com.xpert.dto.agreement.AgreementResponseDTO;
import com.xpert.dto.agreement.CreateAgreementRequestDTO;
import com.xpert.entity.*;
import com.xpert.enums.AgreementStatus;
import com.xpert.enums.AgreementType; 
import com.xpert.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.time.LocalTime;
import java.math.BigDecimal;




import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AgreementServiceImplTest {

    private AgreementRepository agreementRepository;
    private UserRepository userRepository;
    private WorkUnitRepository workUnitRepository;
    private LocationRepository locationRepository;
    private ModelMapper modelMapper;
    private AgreementServiceImpl agreementService;

    @BeforeEach
    void setUp() {
        agreementRepository = mock(AgreementRepository.class);
        userRepository = mock(UserRepository.class);
        workUnitRepository = mock(WorkUnitRepository.class);
        locationRepository = mock(LocationRepository.class);
        modelMapper = new ModelMapper();
        agreementService = new AgreementServiceImpl(agreementRepository, userRepository, workUnitRepository, locationRepository, modelMapper);
    }

    @Test
    void createAgreement_shouldSucceed() {
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(new BigDecimal("1000.00")) 
                .build();

        Users client = new Users();
        Users xpert = new Users();
        WorkUnit workUnit = new WorkUnit();
        Location location = new Location();

        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(xpert));
        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.of(workUnit));
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(agreementRepository.existsByClientAndXpertAndWorkUnit(client, xpert, workUnit)).thenReturn(false);

        Agreement saved = Agreement.builder()
                .id(UUID.randomUUID())
                .client(client)
                .xpert(xpert)
                .workUnit(workUnit)
                .location(location)
                .status(AgreementStatus.PENDING)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(new BigDecimal("1000.00")) 
                .build();

        when(agreementRepository.save(any(Agreement.class))).thenReturn(saved);

        AgreementResponseDTO response = agreementService.createAgreement(dto);
        assertThat(response).isNotNull();
    }

    @Test
    void createAgreement_shouldThrowIfExists() {
        Users client = new Users();
        Users xpert = new Users();
        WorkUnit workUnit = new WorkUnit();
        Location location = new Location();

        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .build();

        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(xpert));
        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.of(workUnit));
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(agreementRepository.existsByClientAndXpertAndWorkUnit(client, xpert, workUnit)).thenReturn(true);

        assertThatThrownBy(() -> agreementService.createAgreement(dto))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Agreement already exists");
    }

    @Test
    void getAgreementById_shouldReturnAgreement() {
        UUID agreementId = UUID.randomUUID();
        Agreement agreement = new Agreement();
        agreement.setId(agreementId);

        when(agreementRepository.findById(agreementId)).thenReturn(Optional.of(agreement));

        AgreementResponseDTO result = agreementService.getAgreementById(agreementId);
        assertThat(result).isNotNull();
    }

    @Test
    void getAgreementById_shouldThrowIfNotFound() {
        UUID agreementId = UUID.randomUUID();
        when(agreementRepository.findById(agreementId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agreementService.getAgreementById(agreementId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Agreement not found");
    }

    @Test
    void getAllAgreements_shouldReturnPagedData() {
        Agreement agreement = new Agreement();
        Page<Agreement> page = new PageImpl<>(Collections.singletonList(agreement));

        when(agreementRepository.findAll(PageRequest.of(0, 5))).thenReturn(page);

        Page<AgreementResponseDTO> result = agreementService.getAllAgreements(0, 5);
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getAllAgreements_shouldThrowIfInvalidParams() {
        assertThatThrownBy(() -> agreementService.getAllAgreements(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void getAllAgreements_withInvalidPagination_shouldThrowException() {
        assertThatThrownBy(() -> agreementService.getAllAgreements(-1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Page index must be non-negative and size must be greater than 0");
    }
    @Test
    void getAllAgreements_withZeroSize_shouldThrowException() {
        assertThatThrownBy(() -> agreementService.getAllAgreements(0, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Page index must be non-negative and size must be greater than 0");
    }
    
    @Test
    void shouldThrowIfClientNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(BigDecimal.valueOf(1000.00))
                .build();

        when(userRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agreementService.createAgreement(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Client not found");
    }
    @Test
    void shouldThrowIfXpertNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(BigDecimal.valueOf(1000.00))
                .build();

        when(userRepository.findById(clientId)).thenReturn(Optional.of(new Users()));
        when(userRepository.findById(xpertId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agreementService.createAgreement(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Xpert not found");
    }

    @Test
    void shouldThrowIfWorkUnitNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(BigDecimal.valueOf(1000.00))
                .build();

        when(userRepository.findById(clientId)).thenReturn(Optional.of(new Users()));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(new Users()));
        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agreementService.createAgreement(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Work unit not found");
    }
    
    @Test
    void shouldThrowIfLocationNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        UUID workUnitId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        CreateAgreementRequestDTO dto = CreateAgreementRequestDTO.builder()
                .clientId(clientId)
                .xpertId(xpertId)
                .workUnitId(workUnitId)
                .locationId(locationId)
                .type(AgreementType.FIXED)
                .totalEstimatedTime(LocalTime.of(2, 0))
                .totalPrice(BigDecimal.valueOf(1000.00))
                .build();

        when(userRepository.findById(clientId)).thenReturn(Optional.of(new Users()));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(new Users()));
        when(workUnitRepository.findById(workUnitId)).thenReturn(Optional.of(new WorkUnit()));
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agreementService.createAgreement(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Location not found");
    }





}