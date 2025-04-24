package com.xpert.service.impl;

import com.xpert.dto.admin.UserAdminResponseDTO;
import com.xpert.entity.Users;
import com.xpert.enums.UserStatus;
import com.xpert.repository.UserRepository;
import com.xpert.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of UserService for admin operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserAdminResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToAdminDTO)
                .toList();
    }

    @Override
    public UserAdminResponseDTO getUserById(UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return mapToAdminDTO(user);
    }

    @Override
    public UserAdminResponseDTO updateUserStatus(UUID userId, UserStatus status) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        user.setStatus(status);
        Users updated = userRepository.save(user);

        return mapToAdminDTO(updated);
    }

    private UserAdminResponseDTO mapToAdminDTO(Users user) {
        return UserAdminResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .status(user.getStatus())
                .isActive(user.getIsActive())
                .isVerified(user.getIsVerified())
                .build();
    }
}
