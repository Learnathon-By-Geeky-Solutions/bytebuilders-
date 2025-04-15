package com.xpert.repository;

import com.xpert.entity.UserDeviceLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDeviceLoginInfoRepository extends JpaRepository<UserDeviceLoginInfo, Long> {
  
}
