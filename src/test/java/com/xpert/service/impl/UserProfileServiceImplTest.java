package com.xpert.service.impl;

import com.xpert.dto.user.UpdateUserProfileDTO;
import com.xpert.dto.user.UserProfileDTO;
import com.xpert.entity.UserProfile;
import com.xpert.entity.Users;
import com.xpert.repository.UserProfileRepository;
import com.xpert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UUID userId;
    private UserProfile profile;
    private UserProfileDTO profileDTO;
    private Users user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new Users();
        user.setId(userId);

        profile = new UserProfile();
        profile.setUserId(userId);
        profile.setUser(user);

        profileDTO = new UserProfileDTO();
    }

    @Test
    void shouldGetUserProfileSuccessfully() {
        given(userProfileRepository.findById(userId)).willReturn(Optional.of(profile));
        given(modelMapper.map(profile, UserProfileDTO.class)).willReturn(profileDTO);

        UserProfileDTO result = userProfileService.getUserProfile(userId);

        assertThat(result).isNotNull();
        then(userProfileRepository).should().findById(userId);
        then(modelMapper).should().map(profile, UserProfileDTO.class);
    }

    @Test
    void shouldThrowWhenUserProfileNotFound() {
        given(userProfileRepository.findById(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userProfileService.getUserProfile(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User profile not found");

        then(userProfileRepository).should().findById(userId);
    }

    @Test
    void shouldUpdateExistingUserProfile() {
        UpdateUserProfileDTO updateDTO = new UpdateUserProfileDTO();
        given(userProfileRepository.findById(userId)).willReturn(Optional.of(profile));
        willDoNothing().given(modelMapper).map(updateDTO, profile);
        given(userProfileRepository.save(profile)).willReturn(profile);
        given(modelMapper.map(profile, UserProfileDTO.class)).willReturn(profileDTO);

        UserProfileDTO result = userProfileService.updateUserProfile(userId, updateDTO);

        assertThat(result).isNotNull();
        then(userProfileRepository).should().save(profile);
    }

    @Test
    void shouldCreateNewUserProfileWhenNotExist() {
        UpdateUserProfileDTO updateDTO = new UpdateUserProfileDTO();
        given(userProfileRepository.findById(userId)).willReturn(Optional.empty());
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        willDoNothing().given(modelMapper).map(updateDTO, profile);
        given(userProfileRepository.save(any(UserProfile.class))).willReturn(profile);
        given(modelMapper.map(profile, UserProfileDTO.class)).willReturn(profileDTO);

        UserProfileDTO result = userProfileService.updateUserProfile(userId, updateDTO);

        assertThat(result).isNotNull();
        then(userRepository).should().findById(userId);
        then(userProfileRepository).should().save(any(UserProfile.class));
    }

    @Test
    void shouldGetNotificationsSuccessfully() {
        List<String> notifications = List.of("Welcome", "Reminder");
        profile.setNotifications(notifications);
        given(userProfileRepository.findById(userId)).willReturn(Optional.of(profile));

        List<String> result = userProfileService.getNotifications(userId);

        assertThat(result).containsExactlyElementsOf(notifications);
    }

    @Test
    void shouldUpdateNotificationsSuccessfully() {
        List<String> notifications = List.of("New Offer");
        given(userProfileRepository.findById(userId)).willReturn(Optional.of(profile));
        given(userProfileRepository.save(profile)).willReturn(profile);

        List<String> result = userProfileService.updateNotifications(userId, notifications);

        assertThat(result).containsExactly("New Offer");
    }
}
