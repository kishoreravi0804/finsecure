package com.project.finsecure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.finsecure.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);

    Optional<Customer> findByCustomerId(String customerId);
}
