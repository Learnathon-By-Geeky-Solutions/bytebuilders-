package com.xpert.repository;

import com.xpert.entity.Agreement;
import com.xpert.entity.Users;
import com.xpert.entity.WorkUnit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {
	
	boolean existsByClientAndXpertAndWorkUnit(Users client, Users xpert, WorkUnit workUnit);

}
