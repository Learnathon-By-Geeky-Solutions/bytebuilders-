package com.xpert.repository;

import com.xpert.entity.UserDeviceLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceLoginInfoRepository extends JpaRepository<UserDeviceLoginInfo, Long> {
    
    // List<UserDeviceLoginInfo> findByUserId(UUID userId);
}
