package com.xpert.controller;

import com.xpert.dto.admin.UserAdminResponseDTO;
import com.xpert.enums.UserStatus;
import com.xpert.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

/**
 * Admin controller for managing platform users.
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    /**
     * Retrieves a list of all users for admin view.
     */
    @GetMapping
    public ResponseEntity<List<UserAdminResponseDTO>> getAllUsers() {
        log.info("Admin requested user list");
        return ResponseEntity.status(OK).body(userService.getAllUsers());
    }

    /**
     * Retrieves details of a specific user by ID.
     *
     * @param userId UUID of the user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserAdminResponseDTO> getUserById(@PathVariable UUID userId) {
        log.info("Admin requested details for user ID: {}", userId);
        return ResponseEntity.status(OK).body(userService.getUserById(userId));
    }

    /**
     * Updates the status of a user (e.g., ACTIVE, INACTIVE, BANNED).
     *
     * @param userId UUID of the user
     * @param status new status to assign
     */
    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserAdminResponseDTO> updateUserStatus(
            @PathVariable UUID userId,
            @RequestParam @NotNull UserStatus status
    ) {
        log.info("Admin updating status of user ID: {} to {}", userId, status);
        return ResponseEntity.status(OK).body(userService.updateUserStatus(userId, status));
    }
}
