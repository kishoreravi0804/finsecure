package com.project.finsecure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.finsecure.entity.Loan;
import com.project.finsecure.repository.LoanRepo;

@Service
public class LoanReportService {

    private final LoanRepo loanRepository;

    public LoanReportService(LoanRepo loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

   
    public Loan getLoanByLoanNumber(String loanNumber) {
        return loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Loan not found"));
    }

  
    public List<Loan> getLoansByCustomerId(String customerId) {
        return loanRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new IllegalArgumentException("Customer not found"));
    }
}
