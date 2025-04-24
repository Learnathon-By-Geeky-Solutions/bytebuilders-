package com.xpert.controller;

import com.xpert.dto.auth.AuthenticationRequest;
import com.xpert.dto.auth.AuthenticationResponse;
import com.xpert.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * Controller for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param request The login request DTO containing email and password
     * @return JWT token response
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        log.info("Received login request for email: {}", request.getEmail());
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.status(OK).body(response);
    }
}
