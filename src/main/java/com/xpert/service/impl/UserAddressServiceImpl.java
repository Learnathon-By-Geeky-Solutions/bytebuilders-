package com.xpert.service.impl;

import com.xpert.dto.address.CreateUserAddressDTO;
import com.xpert.dto.address.UserAddressDTO;
import com.xpert.entity.UserAddress;
import com.xpert.entity.Users;
import com.xpert.repository.UserAddressRepository;
import com.xpert.repository.UserRepository;
import com.xpert.service.UserAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
	
	private static final String ADDRESS_NOT_FOUND = "Address not found";

    private final UserRepository userRepository;

    private final UserAddressRepository addressRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserAddressDTO createAddress(UUID userId, CreateUserAddressDTO dto) {
        Users user = getUser(userId);

        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressRepository.findByUserId(userId)
                    .forEach(addr -> {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    });
        }

        UserAddress address = modelMapper.map(dto, UserAddress.class);
        address.setUser(user);
        address.setIsDeleted(false);

        UserAddress saved = addressRepository.save(address);
        return toDTO(saved);
    }

    @Override
    public List<UserAddressDTO> getUserAddresses(UUID userId) {
        Users user = getUser(userId);
        return addressRepository.findByUserAndIsDeletedFalse(user)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public UserAddressDTO getAddressById(UUID userId, UUID addressId) {
        UserAddress address = addressRepository.findByIdAndUser(addressId, getUser(userId))
                .orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND));

        return toDTO(address);
    }

    @Override
    public UserAddressDTO updateAddress(UUID userId, UUID addressId, CreateUserAddressDTO dto) {
        UserAddress address = addressRepository.findByIdAndUser(addressId, getUser(userId))
                .orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND));

        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressRepository.findByUserId(userId)
                    .forEach(addr -> {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    });
        }

        modelMapper.map(dto, address);  // this updates address object fields with dto values

        UserAddress saved = addressRepository.save(address);
        return toDTO(saved);
    }

    @Override
    public void deleteAddress(UUID userId, UUID addressId) {
        UserAddress address = addressRepository.findByIdAndUser(addressId, getUser(userId))
                .orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND));

        address.setIsDeleted(true);
        addressRepository.save(address);
    }

    @Override
    public UserAddressDTO getDefaultAddress(UUID userId) {
        UserAddress address = addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new RuntimeException("Default address not found"));
        return toDTO(address);
    }

    private UserAddressDTO toDTO(UserAddress address) {
        return modelMapper.map(address, UserAddressDTO.class);
    }

    private Users getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
