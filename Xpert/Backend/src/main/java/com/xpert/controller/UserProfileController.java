package com.xpert.controller;

import com.xpert.dto.user.UpdateUserProfileDTO;
import com.xpert.dto.user.UserProfileDTO;
import com.xpert.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor // Enables constructor-based injection for final fields
public class UserProfileController {

    private final UserProfileService userProfileService; // Constructor injection preferred over field injection

    /**
     * Retrieve the user profile details by user ID.
     * 
     * @param userId UUID of the user
     * @return UserProfileDTO with personal and account info
     */
    
    @GetMapping("/{userId}")
    public UserProfileDTO getUserProfile(@PathVariable UUID userId) {
        return userProfileService.getUserProfile(userId);
    }

    /**
     * Update profile details for the given user.
     * 
     * @param userId UUID of the user
     * @param dto New profile data
     * @return Updated UserProfileDTO
     */
    
    @PutMapping("/{userId}")
    public UserProfileDTO updateUserProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserProfileDTO dto) {
        return userProfileService.updateUserProfile(userId, dto);
    }

    /**
     * Retrieve the list of active notifications for a user.
     * 
     * @param userId UUID of the user
     * @return List of notification strings
     */
    
    @GetMapping("/{userId}/notifications")
    public List<String> getNotifications(@PathVariable UUID userId) {
        return userProfileService.getNotifications(userId);
    }

    /**
     * Replace the user's notifications with a new list.
     * 
     * @param userId UUID of the user
     * @param notifications Updated list of notifications
     * @return Final saved list of notifications
     */
    
    @PutMapping("/{userId}/notifications")
    public List<String> updateNotifications(
            @PathVariable UUID userId,
            @RequestBody List<String> notifications) {
        return userProfileService.updateNotifications(userId, notifications);
    }
}
