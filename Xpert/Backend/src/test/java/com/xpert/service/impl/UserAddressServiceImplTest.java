package com.xpert.service.impl;

import com.xpert.dto.address.CreateUserAddressDTO;
import com.xpert.dto.address.UserAddressDTO;
import com.xpert.entity.UserAddress;
import com.xpert.entity.Users;
import com.xpert.repository.UserAddressRepository;
import com.xpert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAddressServiceImplTest {

    private UserAddressRepository addressRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserAddressServiceImpl service;

    private final UUID userId = UUID.randomUUID();
    private final UUID addressId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        addressRepository = mock(UserAddressRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        service = new UserAddressServiceImpl(userRepository, addressRepository, modelMapper);
    }

    @Test
    void createAddress_shouldCreateSuccessfully() {
        Users user = new Users();
        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .isDefault(true)
                .city("Dhaka")
                .country("Bangladesh")
                .zipCode("1200")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = service.createAddress(userId, dto);

        assertThat(result).isNotNull();
        verify(addressRepository, times(1)).save(any(UserAddress.class));
    }

    @Test
    void getUserAddresses_shouldReturnAddresses() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByUserAndIsDeletedFalse(user)).thenReturn(List.of(address));

        List<UserAddressDTO> addresses = service.getUserAddresses(userId);

        assertThat(addresses).hasSize(1);
    }

    @Test
    void getAddressById_shouldReturnCorrectAddress() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));

        UserAddressDTO result = service.getAddressById(userId, addressId);

        assertThat(result).isNotNull();
    }

    @Test
    void updateAddress_shouldUpdateSuccessfully() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .city("Chattogram")
                .country("Bangladesh")
                .zipCode("4000")
                .isDefault(true)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));
        when(addressRepository.findByUserId(userId)).thenReturn(Collections.singletonList(address));
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = service.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
    }

    @Test
    void deleteAddress_shouldMarkDeleted() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));

        service.deleteAddress(userId, addressId);

        verify(addressRepository).save(address);
        assertThat(address.getIsDeleted()).isTrue();
    }

    @Test
    void getDefaultAddress_shouldReturnIfExists() {
        UserAddress address = new UserAddress();
        when(addressRepository.findByUserIdAndIsDefaultTrue(userId)).thenReturn(Optional.of(address));

        UserAddressDTO result = service.getDefaultAddress(userId);

        assertThat(result).isNotNull();
    }

    @Test
    void createAddress_shouldThrowWhenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        CreateUserAddressDTO dto = new CreateUserAddressDTO();
        assertThatThrownBy(() -> service.createAddress(userId, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void getDefaultAddress_shouldThrowWhenMissing() {
        when(addressRepository.findByUserIdAndIsDefaultTrue(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getDefaultAddress(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Default address not found");
    }

    @Test
    void getAddressById_shouldThrowWhenAddressMissing() {
        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getAddressById(userId, addressId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found");
    }

    @Test
    void updateAddress_shouldThrowWhenAddressMissing() {
        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.empty());

        CreateUserAddressDTO dto = new CreateUserAddressDTO();
        assertThatThrownBy(() -> service.updateAddress(userId, addressId, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found");
    }

    @Test
    void deleteAddress_shouldThrowWhenAddressMissing() {
        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteAddress(userId, addressId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found");
    }
}
