package com.project.finsecure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.finsecure.entity.Loan;
import com.project.finsecure.service.LoanReportService;

@RestController
@RequestMapping("/api/reports")
public class LoanReportController {

    private final LoanReportService loanReportService;

    public LoanReportController(LoanReportService loanReportService) {
        this.loanReportService = loanReportService;
    }

   
    @GetMapping("/loans")
    public List<Loan> getAllLoans() {
        return loanReportService.getAllLoans();
    }

    
    @GetMapping("/loans/{loanNumber}")
    public Loan getLoanByLoanNumber(
            @PathVariable String loanNumber) {
        return loanReportService.getLoanByLoanNumber(loanNumber);
    }

   
    @GetMapping("/customers/{customerId}/loans")
    public List<Loan> getLoansByCustomer(
            @PathVariable String customerId) {
        return loanReportService.getLoansByCustomerId(customerId);
    }
}
