package com.xpert.repository;

import com.xpert.entity.WorkUnit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, UUID> {
}
