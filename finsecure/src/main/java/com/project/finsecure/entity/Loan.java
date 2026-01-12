package com.project.finsecure.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @Column(name = "loan_number")
    private String loanNumber;

   @Column(name = "customer_id")
    private String customerId;

    @Column(name = "loan_amount")
    private Long loanAmount;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "tenure_months")
    private int tenureMonths;

    @Column(name = "emi_amount")
    private Long emiAmount;
   
    @Column(name= "status")
    private String status;

    @Column(name = "agreemented_date")
    private LocalDate agreementedDate;

    @Column(name = "start_date")
    private LocalDate StartDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}
