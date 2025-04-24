package com.xpert.service;

import com.xpert.dto.address.CreateUserAddressDTO;
import com.xpert.dto.address.UserAddressDTO;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {

    UserAddressDTO createAddress(UUID userId, CreateUserAddressDTO dto);

    List<UserAddressDTO> getUserAddresses(UUID userId);

    UserAddressDTO getAddressById(UUID userId, UUID addressId);

    UserAddressDTO updateAddress(UUID userId, UUID addressId, CreateUserAddressDTO dto);

    void deleteAddress(UUID userId, UUID addressId);

    UserAddressDTO getDefaultAddress(UUID userId);
}
