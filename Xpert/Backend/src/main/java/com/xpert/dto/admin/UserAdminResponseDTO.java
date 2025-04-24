package com.xpert.dto.admin;

import com.xpert.enums.UserRole;
import com.xpert.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DTO used by admin to view user details.
 */
@Data
@Builder
@AllArgsConstructor
public class UserAdminResponseDTO {

    /**
     * Unique identifier of the user.
     */
    private UUID id;

    /**
     * User's full name (concatenated from first and last name).
     */
    private String fullName;

    /**
     * Encrypted email (decrypted for admin view).
     */
    private String email;

    /**
     * Encrypted phone number (decrypted for admin view).
     */
    private String phone;

    /**
     * Role of the user (ADMIN, CUSTOMER, XPERT).
     */
    private UserRole role;

    /**
     * Status of the user (ACTIVE, INACTIVE, BANNED, etc).
     */
    private UserStatus status;

    /**
     * Indicates whether the user is active and allowed to login.
     */
    private boolean isActive;

    /**
     * Indicates if the user has verified their email/phone.
     */
    private boolean isVerified;
}
