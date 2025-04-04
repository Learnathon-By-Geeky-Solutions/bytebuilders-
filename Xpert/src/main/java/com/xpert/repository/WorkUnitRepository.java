package com.xpert.repository;

import com.xpert.entity.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkUnitRepository extends JpaRepository<WorkUnit, Integer> {
}
