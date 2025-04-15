package com.xpert.controller;

import com.xpert.dto.address.CreateUserAddressDTO;
import com.xpert.dto.address.UserAddressDTO;
import com.xpert.service.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-addresses")
@RequiredArgsConstructor
public class UserAddressController {

  
    private final UserAddressService userAddressService;

    /**
     * Create a new address for the given user.
     * @param userId User's UUID
     * @param dto Address data to be created
     * @return Created address as DTO
     */
    @PostMapping("/{userId}")
    public ResponseEntity<UserAddressDTO> createAddress(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateUserAddressDTO dto) {
        return ResponseEntity.ok(userAddressService.createAddress(userId, dto));
    }

    /**
     * Get all active (non-deleted) addresses for the given user.
     * @param userId User's UUID
     * @return List of addresses
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserAddressDTO>> getUserAddresses(@PathVariable UUID userId) {
        return ResponseEntity.ok(userAddressService.getUserAddresses(userId));
    }

    /**
     * Get the default address for the user (isDefault = true).
     * @param userId User's UUID
     * @return Default address as DTO
     */
    @GetMapping("/{userId}/default")
    public ResponseEntity<UserAddressDTO> getDefaultAddress(@PathVariable UUID userId) {
        return ResponseEntity.ok(userAddressService.getDefaultAddress(userId));
    }

    /**
     * Get a specific address by ID for the given user.
     * @param userId User's UUID
     * @param addressId Address ID to fetch
     * @return Address DTO
     */
    @GetMapping("/{userId}/{addressId}")
    public ResponseEntity<UserAddressDTO> getAddressById(
            @PathVariable UUID userId,
            @PathVariable UUID addressId) {
        return ResponseEntity.ok(userAddressService.getAddressById(userId, addressId));
    }

    /**
     * Update a specific address for a user.
     * @param userId User's UUID
     * @param addressId Address ID to update
     * @param dto Updated address data
     * @return Updated address DTO
     */
    @PutMapping("/{userId}/{addressId}")
    public ResponseEntity<UserAddressDTO> updateAddress(
            @PathVariable UUID userId,
            @PathVariable UUID addressId,
            @Valid @RequestBody CreateUserAddressDTO dto) {
        return ResponseEntity.ok(userAddressService.updateAddress(userId, addressId, dto));
    }

    /**
     * Soft-delete a specific address (set isDeleted = true).
     * @param userId User's UUID
     * @param addressId Address ID to delete
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{userId}/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID userId,
            @PathVariable UUID addressId) {
        userAddressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
