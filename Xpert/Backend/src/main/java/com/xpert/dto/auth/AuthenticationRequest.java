package com.xpert.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for handling user login request.
 * Requires valid email and password fields.
 */
@Data
public class AuthenticationRequest {

    /**
     * User email address (used as login identifier).
     */
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    /**
     * Plaintext password to be matched with hashed value.
     */
    @NotBlank(message = "Password must not be blank")
    private String password;
}
