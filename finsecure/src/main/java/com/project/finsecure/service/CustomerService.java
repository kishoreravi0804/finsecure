package com.project.finsecure.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.finsecure.entity.Customer;
import com.project.finsecure.repository.CustomerRepo;

@Service
public class CustomerService {

    private final CustomerRepo CustomerRepo;

    public CustomerService(CustomerRepo CustomerRepo) {
        this.CustomerRepo = CustomerRepo;
    }

    public String findOrCreateCustomerId(String mobileNumber) {

        Optional<Customer> optionalCustomer =
                CustomerRepo.findByMobileNumber(mobileNumber);

        
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().getCustomerId();
        }

        
        Customer customer = new Customer();
        customer.setMobileNumber(mobileNumber);
        customer.setCustomerId(generateCustomerId());

        CustomerRepo.save(customer);

        return customer.getCustomerId();
    }

    private String generateCustomerId() {
        long count = CustomerRepo.count() + 1;
        return String.format("CUST%06d", count);
    }
}
