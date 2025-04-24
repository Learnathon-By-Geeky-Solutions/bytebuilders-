package com.xpert.service;

import com.xpert.dto.auth.AuthenticationRequest;
import com.xpert.dto.auth.AuthenticationResponse;
import com.xpert.dto.auth.RegisterRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * Service interface for handling user authentication logic.
 */
public interface AuthenticationService {

    /**
     * Authenticates the user and returns a JWT token if successful.
     *
     * @param request The user's login credentials
     * @return JWT token wrapped in AuthenticationResponse
     * @throws EntityNotFoundException if the user is not found
     * @throws BadCredentialsException if the password is incorrect
     */
    AuthenticationResponse login(AuthenticationRequest request);

    /**
     * Registers a new user and returns a JWT token if successful.
     *
     * @param request Registration request containing user details
     * @return JWT token wrapped in AuthenticationResponse
     * @throws IllegalArgumentException if email or phone already exists
     */
    AuthenticationResponse register(RegisterRequestDTO request);
}
