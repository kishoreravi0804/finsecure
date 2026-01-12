package com.project.finsecure.service;

import org.springframework.stereotype.Component;

import com.project.finsecure.repository.LoanRepo;

@Component
public class LoanNumberGenerator {

    private final LoanRepo loanRepo;

    public LoanNumberGenerator(LoanRepo loanRepo){
         this.loanRepo = loanRepo;
    }
    
   

    public  String generate() {
      
       int max = loanRepo.findMaxLoanNumber();
       int next = max+1;

       return String.format("LN%06d",next);
  

    }
}
