package com.xpert.repository;

import com.xpert.entity.Agreement;
import com.xpert.entity.Users;
import com.xpert.entity.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {

    /**
     * Checks if an agreement exists between a client, expert, and work unit.
     *
     * @param client   the client user
     * @param xpert    the expert user
     * @param workUnit the work unit
     * @return true if such an agreement exists, false otherwise
     */
    boolean existsByClientAndXpertAndWorkUnit(Users client, Users xpert, WorkUnit workUnit);
}
