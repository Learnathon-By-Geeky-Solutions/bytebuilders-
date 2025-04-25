package com.xpert.service.impl;

import com.xpert.dto.admin.UserAdminResponseDTO;
import com.xpert.entity.Users;
import com.xpert.enums.UserRole;
import com.xpert.enums.UserStatus;
import com.xpert.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getAllUsers_shouldReturnMappedList() {
        Users user = buildUser();
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserAdminResponseDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getUserById_shouldReturnUser() {
        Users user = buildUser();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserAdminResponseDTO result = userService.getUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void updateUserStatus_shouldUpdateStatus() {
        Users user = buildUser();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserAdminResponseDTO result = userService.updateUserStatus(userId, UserStatus.SUSPENDED);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.SUSPENDED);
        verify(userRepository).save(user);
    }

    @Test
    void updateUserStatus_shouldThrowIfNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUserStatus(userId, UserStatus.ACTIVE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    private Users buildUser() {
        return Users.builder()
                .id(userId)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phone("+880123456789")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .isActive(true)
                .isVerified(true)
                .build();
    }
}
