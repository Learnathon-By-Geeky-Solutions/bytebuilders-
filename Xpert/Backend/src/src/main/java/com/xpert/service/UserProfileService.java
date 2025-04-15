package com.xpert.service;

import com.xpert.dto.user.UpdateUserProfileDTO;
import com.xpert.dto.user.UserProfileDTO;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    UserProfileDTO getUserProfile(UUID userId);

    UserProfileDTO updateUserProfile(UUID userId, UpdateUserProfileDTO dto);

    List<String> getNotifications(UUID userId);

    List<String> updateNotifications(UUID userId, List<String> notifications);
}
