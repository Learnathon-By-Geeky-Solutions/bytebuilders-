package com.xpert.service.impl;

import com.xpert.dto.auth.AuthenticationRequest;
import com.xpert.dto.auth.AuthenticationResponse;
import com.xpert.entity.Users;
import com.xpert.repository.UserRepository;
import com.xpert.security.JwtService;
import com.xpert.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of AuthenticationService for handling login logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        log.info("Attempting login for email: {}", request.getEmail());

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            log.warn("Authentication failed for user: {} — invalid credentials", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        log.info("Authentication successful for user: {}", request.getEmail());

        return AuthenticationResponse.builder().token(token).build();
    }
}
