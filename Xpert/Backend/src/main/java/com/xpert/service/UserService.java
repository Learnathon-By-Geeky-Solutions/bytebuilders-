package com.xpert.service;

import com.xpert.dto.admin.UserAdminResponseDTO;
import com.xpert.enums.UserStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for admin-related user management.
 */
public interface UserService {

    /**
     * Retrieves all users for administrative review.
     *
     * @return a list of users with basic info for admin
     */
    List<UserAdminResponseDTO> getAllUsers();

    /**
     * Retrieves a user by ID for admin view.
     *
     * @param userId UUID of the user
     * @return user data
     */
    UserAdminResponseDTO getUserById(UUID userId);

    /**
     * Updates the status (ACTIVE, INACTIVE, BANNED, etc.) of a user.
     *
     * @param userId UUID of the user
     * @param status new status to assign
     * @return updated user data
     */
    UserAdminResponseDTO updateUserStatus(UUID userId, UserStatus status);
}
