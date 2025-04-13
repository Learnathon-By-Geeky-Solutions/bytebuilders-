package com.xpert.entity;

import com.xpert.converter.EncryptedStringConverter;
import com.xpert.enums.UserRole;
import com.xpert.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a registered user of the platform.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
public class Users {

    /**
     * Primary key (UUID). Secure and unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Encrypted email address (used for login & identity).
     */
    @Convert(converter = EncryptedStringConverter.class)
    @Column(nullable = false, length = 255, unique = true)
    private String email;

    /**
     * Encrypted phone number (used for 2FA / contact).
     */
    @Convert(converter = EncryptedStringConverter.class)
    @Column(nullable = false, length = 255, unique = true)
    private String phone;

    /**
     * Hashed password string (never store plain text).
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * Email or phone verification flag.
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean isVerified = false;

    /**
     * Account creation timestamp.
     */
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * Last update timestamp.
     */
    @Builder.Default
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    /**
     * User's first name (used for profile display).
     */
    @Column(nullable = false, length = 20)
    private String firstName;

    /**
     * User's last name (used for profile display).
     */
    @Column(nullable = false, length = 20)
    private String lastName;

    /**
     * Current status of the user (ACTIVE, INACTIVE, etc).
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserStatus status = UserStatus.INACTIVE;

    /**
     * Role of the user (ADMIN, XPERT, CUSTOMER).
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserRole role = UserRole.CUSTOMER;

    /**
     * Whether the user is active and allowed to login.
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = false;
}
