package com.xpert.repository;

import com.xpert.entity.PaymentMethod;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
}
