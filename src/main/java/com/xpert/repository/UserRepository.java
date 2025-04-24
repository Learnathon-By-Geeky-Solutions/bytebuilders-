package com.xpert.repository;

import com.xpert.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    /**
     * Finds a user by email (used for login).
     */
    Optional<Users> findByEmail(String email);

    /**
     * Checks if a user exists with the given email.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given phone number.
     */
    boolean existsByPhone(String phone);
}
