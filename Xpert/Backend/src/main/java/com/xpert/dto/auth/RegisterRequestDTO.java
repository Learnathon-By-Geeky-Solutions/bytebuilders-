package com.xpert.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * DTO representing a user registration request.
 * Used for the /api/auth/register endpoint.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "password")

public class RegisterRequestDTO {

    /**
     * User's first name.
     */
    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    /**
     * User's last name.
     */
    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    /**
     * User's email address (used for login).
     */
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    /**
     * User's phone number (must be digits only).
     */
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be exactly 11 digits")
    private String phone;

    /**
     * User's password (plaintext, to be hashed).
     */
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
