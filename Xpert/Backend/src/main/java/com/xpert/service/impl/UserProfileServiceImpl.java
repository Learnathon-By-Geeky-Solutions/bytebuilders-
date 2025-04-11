package com.xpert.service.impl;

import com.xpert.dto.user.UpdateUserProfileDTO;
import com.xpert.dto.user.UserProfileDTO;
import com.xpert.entity.UserProfile;
import com.xpert.entity.Users;
import com.xpert.repository.UserProfileRepository;
import com.xpert.repository.UserRepository;
import com.xpert.service.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserProfileDTO getUserProfile(UUID userId) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        return toDTO(profile);
    }

    @Override
    public UserProfileDTO updateUserProfile(UUID userId, UpdateUserProfileDTO dto) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseGet(() -> {
                    Users user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);
                    newProfile.setUserId(user.getId());
                    return newProfile;
                });

        modelMapper.map(dto, profile);  // Apply updates from DTO to existing entity

        UserProfile saved = userProfileRepository.save(profile);
        return toDTO(saved);
    }

    @Override
    public List<String> getNotifications(UUID userId) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        return profile.getNotifications();
    }

    @Override
    public List<String> updateNotifications(UUID userId, List<String> notifications) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        profile.setNotifications(notifications);
        userProfileRepository.save(profile);
        return notifications;
    }

    private UserProfileDTO toDTO(UserProfile profile) {
        return modelMapper.map(profile, UserProfileDTO.class);
    }
}
