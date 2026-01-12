package com.project.finsecure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.finsecure.service.LoanService;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final LoanService loanService;

    public AgentController(LoanService loanService) {
        this.loanService = loanService;
    }


    @PostMapping("/pay-emi")
    public ResponseEntity<String> payEmi(
            @RequestParam String loanNumber,
            @RequestParam int emiNumber) {

        loanService.payEmi(loanNumber,emiNumber);

        return ResponseEntity.ok("EMI paid successfully");
    }
}
