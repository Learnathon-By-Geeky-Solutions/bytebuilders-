package com.xpert.repository;

import com.xpert.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
}
