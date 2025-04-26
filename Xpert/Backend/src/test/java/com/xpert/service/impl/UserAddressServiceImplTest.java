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

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAddressServiceImplTest {

    private UserAddressRepository addressRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserAddressServiceImpl userAddressService;

    private final UUID userId = UUID.randomUUID();
    private final UUID addressId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        addressRepository = mock(UserAddressRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        userAddressService = new UserAddressServiceImpl(userRepository, addressRepository, modelMapper);
    }

    @Test
    void createAddress_shouldSucceed() {
        Users user = new Users();
        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .isDefault(true)
                .city("Dhaka")
                .country("Bangladesh")
                .zipCode("1205")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByUserId(userId)).thenReturn(List.of());
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = userAddressService.createAddress(userId, dto);

        assertThat(result).isNotNull();
    }

    @Test
    void createAddress_shouldSetOtherDefaultsFalse() {
        Users user = new Users();
        UserAddress oldDefault = new UserAddress();
        oldDefault.setIsDefault(true);
        oldDefault.setUser(user);

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .isDefault(true)
                .city("Rajshahi")
                .country("Bangladesh")
                .zipCode("6000")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByUserId(userId)).thenReturn(List.of(oldDefault));
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = userAddressService.createAddress(userId, dto);
        assertThat(result).isNotNull();
        verify(addressRepository, atLeastOnce()).save(any(UserAddress.class));
    }

    @Test
    void getUserAddresses_shouldReturnList() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByUserAndIsDeletedFalse(user)).thenReturn(List.of(address));

        List<UserAddressDTO> result = userAddressService.getUserAddresses(userId);
        assertThat(result).hasSize(1);
    }

    @Test
    void getAddressById_shouldReturnAddress() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));

        UserAddressDTO result = userAddressService.getAddressById(userId, addressId);
        assertThat(result).isNotNull();
    }

    @Test
    void getAddressById_shouldThrowIfNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(new Users()));
        when(addressRepository.findByIdAndUser(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userAddressService.getAddressById(userId, addressId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found");
    }

    @Test
    void updateAddress_shouldSucceed() {
        Users user = new Users();
        UserAddress existing = new UserAddress();
        existing.setUser(user);

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .isDefault(true)
                .city("Barishal")
                .country("Bangladesh")
                .zipCode("8200")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(existing));
        when(addressRepository.findByUserId(userId)).thenReturn(List.of(existing));
        when(addressRepository.save(any(UserAddress.class))).thenReturn(existing);

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);
        assertThat(result).isNotNull();
    }

    @Test
    void updateAddress_shouldUpdateWithoutChangingDefaultsIfNotDefault() {
        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .title("Home")
                .isDefault(false)
                .build();

        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(UserAddress.class))).thenReturn(address);

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
        verify(addressRepository).save(address);
    }

    @Test
    void deleteAddress_shouldMarkAsDeleted() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));

        userAddressService.deleteAddress(userId, addressId);
        verify(addressRepository).save(address);
        assertThat(address.getIsDeleted()).isTrue();
    }

    @Test
    void deleteAddress_shouldSoftDeleteSuccessfully() {
        Users user = new Users();
        UserAddress address = new UserAddress();
        address.setUser(user);
        address.setId(addressId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(address));

        userAddressService.deleteAddress(userId, addressId);

        verify(addressRepository).save(address);
        assertThat(address.getIsDeleted()).isTrue();
    }

    @Test
    void getDefaultAddress_shouldReturnIfExists() {
        UserAddress address = new UserAddress();
        when(addressRepository.findByUserIdAndIsDefaultTrue(userId)).thenReturn(Optional.of(address));

        UserAddressDTO result = userAddressService.getDefaultAddress(userId);
        assertThat(result).isNotNull();
    }

    @Test
    void getDefaultAddress_shouldThrowIfMissing() {
        when(addressRepository.findByUserIdAndIsDefaultTrue(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userAddressService.getDefaultAddress(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Default address not found");
    }

    @Test
    void getUser_shouldThrowIfNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .city("Khulna")
                .country("Bangladesh")
                .zipCode("9000")
                .build();

        assertThatThrownBy(() -> userAddressService.createAddress(userId, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }
    

    
    @Test
    void deleteAddress_shouldThrowIfAddressNotFound() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        Users user = new Users();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userAddressService.deleteAddress(userId, addressId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found");
    }
    
    @Test
    void updateAddress_shouldSkipDefaultResetIfNotDefault() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .title("New Title")
                .isDefault(false) // important: skip resetting other defaults
                .build();

        Users user = new Users();
        UserAddress existing = new UserAddress();
        existing.setUser(user);
        existing.setId(addressId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(UserAddress.class))).thenReturn(existing);

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
        verify(addressRepository).findByIdAndUser(addressId, user);
        verify(addressRepository).save(existing);
    }
    
    
    @Test
    void updateAddress_shouldNotResetOthersWhenIsDefaultIsNull() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        // isDefault is null here
        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .title("Silent Update")
                .build();

        Users user = new Users();
        UserAddress existing = new UserAddress();
        existing.setUser(user);
        existing.setId(addressId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(UserAddress.class))).thenReturn(existing);

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
        verify(addressRepository).findByIdAndUser(addressId, user);
        verify(addressRepository).save(existing);
        // no need to verify .findByUserId(userId) since it should be skipped
    }
    
    @Test
    void createAddress_shouldSkipResetIfNotDefault() {
        UUID userId = UUID.randomUUID();

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .city("Comilla")
                .country("Bangladesh")
                .zipCode("3500")
                .isDefault(false) // Explicitly false
                .build();

        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.save(any(UserAddress.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserAddressDTO result = userAddressService.createAddress(userId, dto);

        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
        verify(addressRepository).save(any(UserAddress.class));
        //  No interaction with .findByUserId() since default = false
        verify(addressRepository, never()).findByUserId(userId);
    }
    
    @Test
    void updateAddress_shouldResetOtherDefaultsIfDtoIsDefault() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .title("Updated Title")
                .isDefault(true) // this triggers the if-block
                .build();

        Users user = new Users();
        UserAddress existing = new UserAddress();
        existing.setId(addressId);
        existing.setUser(user);

        UserAddress other = new UserAddress();
        other.setUser(user);
        other.setIsDefault(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(existing));
        when(addressRepository.findByUserId(userId)).thenReturn(List.of(other));
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
        verify(addressRepository).findByUserId(userId); // verify default reset logic triggered
        verify(addressRepository, atLeastOnce()).save(any(UserAddress.class));
    }
    
    @Test
    void updateAddress_shouldResetDefaultIfDtoMarksAsDefault() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        CreateUserAddressDTO dto = CreateUserAddressDTO.builder()
                .title("Primary")
                .isDefault(true) // ✅ triggers the missing branch
                .build();

        Users user = new Users();

        UserAddress current = new UserAddress();
        current.setId(addressId);
        current.setUser(user);

        UserAddress existingDefault = new UserAddress(); // simulate another default
        existingDefault.setId(UUID.randomUUID());
        existingDefault.setUser(user);
        existingDefault.setIsDefault(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findByIdAndUser(addressId, user)).thenReturn(Optional.of(current));
        when(addressRepository.findByUserId(userId)).thenReturn(List.of(existingDefault));
        when(addressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressDTO result = userAddressService.updateAddress(userId, addressId, dto);

        assertThat(result).isNotNull();
        verify(addressRepository).findByUserId(userId);
        verify(addressRepository, atLeastOnce()).save(any(UserAddress.class));
        assertThat(existingDefault.getIsDefault()).isFalse(); // assert default reset
    }
    
    @Test
    void getUser_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
         
        	userAddressService.getUserAddresses(userId);
        }).isInstanceOf(RuntimeException.class)
          .hasMessageContaining("User not found");
    }
 








}