package com.xpert.service.impl;

import com.xpert.dto.auth.AuthenticationRequest;
import com.xpert.dto.auth.AuthenticationResponse;
import com.xpert.dto.auth.RegisterRequestDTO;
import com.xpert.entity.Users;
import com.xpert.enums.UserRole;
import com.xpert.repository.UserRepository;
import com.xpert.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationService = new AuthenticationServiceImpl(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void login_shouldSucceed() {
        AuthenticationRequest request = AuthenticationRequest.builder()
            .email("user@example.com")
            .password("password123")
            .build();

        Users user = new Users();
        user.setEmail("user@example.com");
        user.setPasswordHash("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("user@example.com")).thenReturn("mocked-token");

        AuthenticationResponse response = authenticationService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mocked-token");
    }


    @Test
    void login_shouldThrowIfUserNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("notfound@example.com")
                .password("pwd")
                .build();

        assertThatThrownBy(() -> authenticationService.login(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found");
    }


    @Test
    void login_shouldThrowIfPasswordInvalid() {
        Users user = new Users();
        user.setEmail("user@example.com");
        user.setPasswordHash("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("user@example.com")
                .password("wrongPassword")
                .build();

        assertThatThrownBy(() -> authenticationService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Invalid email or password");
    }


    @Test
    void register_shouldSucceed() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("0123456789")
                .password("securePwd")
                .build();

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.existsByPhone("0123456789")).thenReturn(false);
        when(passwordEncoder.encode("securePwd")).thenReturn("encodedPwd");
        when(jwtService.generateToken("john@example.com")).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.register(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(userRepository).save(Mockito.any(Users.class));
    }


    @Test
    void register_shouldThrowIfEmailExists() {
        when(userRepository.existsByEmail("exist@example.com")).thenReturn(true);

        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .firstName("Test")
                .lastName("User")
                .email("exist@example.com")
                .phone("0123456789")
                .password("dummyPwd")
                .build();

        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email is already registered");
    }


    @Test
    void register_shouldThrowIfPhoneExists() {
        when(userRepository.existsByEmail("unique@example.com")).thenReturn(false);
        when(userRepository.existsByPhone("0123456789")).thenReturn(true);

        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .firstName("Test")
                .lastName("User")
                .email("unique@example.com")
                .phone("0123456789")
                .password("dummyPwd")
                .build();

        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phone number is already registered");
    }

}
