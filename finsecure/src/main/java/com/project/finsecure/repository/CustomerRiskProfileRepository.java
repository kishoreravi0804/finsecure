package com.project.finsecure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.finsecure.entity.CustomerRiskProfile;

public interface CustomerRiskProfileRepository
        extends JpaRepository<CustomerRiskProfile, Long> {

    Optional<CustomerRiskProfile> findByCustomerId(String customerId);
}
