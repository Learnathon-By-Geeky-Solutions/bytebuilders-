package com.xpert.repository;

import com.xpert.entity.UserAddress;
import com.xpert.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {

    List<UserAddress> findByUserAndIsDeletedFalse(Users user);

    Optional<UserAddress> findByIdAndUser(UUID id, Users user);

    List<UserAddress> findByUserId(UUID userId);

    Optional<UserAddress> findByUserIdAndIsDefaultTrue(UUID userId);
}
