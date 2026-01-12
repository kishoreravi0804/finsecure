package com.project.finsecure.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.finsecure.dto.CreateLoanRequest;
import com.project.finsecure.entity.Loan;
import com.project.finsecure.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

   
    @PostMapping
    public ResponseEntity<Loan> createLoan(
           @Valid @RequestBody CreateLoanRequest request
           ) {

        Loan loan = loanService.createLoan(
                request.getMobileNumber(),
                request.getRequestedAmount(),
                request.getCibilScore(),
                request.getTenureMonths()
                
        );

        return ResponseEntity.ok(loan);
    }

 
}
