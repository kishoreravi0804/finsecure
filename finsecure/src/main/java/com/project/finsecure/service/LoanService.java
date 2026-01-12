package com.project.finsecure.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.finsecure.audit.AuditAction;
import com.project.finsecure.audit.EntityType;
import com.project.finsecure.entity.CustomerRiskProfile;
import com.project.finsecure.entity.Emi;
import com.project.finsecure.entity.Loan;
import com.project.finsecure.enums.RiskStatus;
import com.project.finsecure.repository.CustomerRiskProfileRepository;
import com.project.finsecure.repository.EmiRepo;
import com.project.finsecure.repository.LoanRepo;

@Service
public class LoanService {

    private final LoanRepo loanRepository;
    private final EmiRepo emiRepository;
    private final AuditService auditService;
    private final CustomerService customerService;
    private final  CustomerRiskProfileService  customerRiskProfileService;
    private final LoanNumberGenerator  loanNumberGenerator;
    private final CustomerRiskProfileRepository  customerRiskProfileRepository;

    public LoanService(
            LoanRepo loanRepository,
            EmiRepo emiRepository,
            AuditService auditService,
            CustomerService customerService,
           CustomerRiskProfileService  customerRiskProfileService,
         LoanNumberGenerator  loanNumberGenerator,
        CustomerRiskProfileRepository  customerRiskProfileRepository) {

        this.loanRepository = loanRepository;
        this.emiRepository = emiRepository;
        this.auditService = auditService;
        this.customerService = customerService;
        this.customerRiskProfileService = customerRiskProfileService;
        this.loanNumberGenerator = loanNumberGenerator;
        this.customerRiskProfileRepository = customerRiskProfileRepository;
    }

    @Transactional
public Loan createLoan(
        String mobileNumber,
        Long requestedAmount,
        int cibilScore,
        int tenureMonths) {

    
    if (requestedAmount < 10_000 || requestedAmount > 20_00_000) {
        throw new IllegalArgumentException("Loan amount must be between 10K and 20L");
    }

   
    if (cibilScore < 300 || cibilScore > 900) {
        throw new IllegalArgumentException("Invalid CIBIL score");
    }

   
    if (tenureMonths <=11|| tenureMonths>=61) {
        throw new IllegalArgumentException("Invalid tenure");
    }


    
    String customerId = customerService.findOrCreateCustomerId(mobileNumber);

    
    CustomerRiskProfile riskProfile =
            customerRiskProfileService.getOrCreate(customerId);

    
    if (riskProfile.getRiskStatus() == RiskStatus.BLACKLISTED) {
        throw new IllegalStateException("Customer is blacklisted due to past defaults");
    }

  
    Long approvedAmount;

    if (cibilScore >= 750) {
        approvedAmount = requestedAmount;
    } else if (cibilScore >= 700) {
        approvedAmount = Math.round(requestedAmount * 0.65);
    } else {
        throw new IllegalStateException("CIBIL score too low for loan approval");
    }

   
    String loanNumber = loanNumberGenerator.generate();
    double interestRate = 12.5; 
    LocalDate agreementDate = LocalDate.now();
    LocalDate startDate = agreementDate.plusMonths(1); 
    LocalDate endDate = agreementDate.plusMonths(tenureMonths+1);

    double totalInterest =
            (approvedAmount * interestRate * tenureMonths) / (12 * 100);

    long totalPayable = Math.round(approvedAmount + totalInterest);

    long emiAmount = totalPayable / tenureMonths;

    
    Loan loan = new Loan();
    loan.setLoanNumber(loanNumber);
    loan.setCustomerId(customerId);
    loan.setLoanAmount(approvedAmount);
    loan.setEmiAmount(emiAmount);
    loan.setInterestRate(interestRate);
    loan.setTenureMonths(tenureMonths);
    loan.setAgreementedDate(agreementDate);
    loan.setStartDate(startDate);
    loan.setEndDate(endDate);
    loan.setStatus("ACTIVE");

    loanRepository.save(loan);
   
    
    generateEmis(
            loanNumber,
            approvedAmount,
            interestRate,
            tenureMonths,
            startDate
    );

   
    riskProfile.setTotalLoans(riskProfile.getTotalLoans() + 1);
    riskProfile.setActiveLoans(riskProfile.getActiveLoans() + 1);
    customerRiskProfileRepository.save(riskProfile);

   
    auditService.logAction(
           
            AuditAction.CREATE_LOAN,
            EntityType.LOAN,
            loan.getId()
    );

    return loan;
}

 private void generateEmis(
        String loanNumber,
        Long principalAmount,
        double interestRate,
        int tenureMonths,
        LocalDate startDate) {

  
    double totalInterest =
            (principalAmount * interestRate * tenureMonths) / (12 * 100);

    long totalPayable = Math.round(principalAmount + totalInterest);

    long emiAmount = totalPayable / tenureMonths;

    for (int i = 1; i <= tenureMonths; i++) {

        Emi emi = new Emi();
        emi.setLoanNumber(loanNumber);
        emi.setEmiNumber(i);
        emi.setEmiAmount(emiAmount);
        emi.setDueDate(startDate.plusMonths(i - 1));
        emi.setPaid(false);
        emi.setPaidDate(null);

        emiRepository.save(emi);
    }
}

   @Transactional
public void payEmi(String loanNumber, int emiNumber) {

   
    Emi emi = emiRepository
            .findByLoanNumberAndEmiNumber(loanNumber, emiNumber)
            .orElseThrow(() ->
                    new IllegalArgumentException("EMI not found"));


    if (emi.isPaid()) {
        throw new IllegalStateException("EMI already paid");
    }

    LocalDate today = LocalDate.now();

   
    emi.setPaid(true);
    emi.setPaidDate(today);
    emiRepository.save(emi);

    
    boolean isOverdue = today.isAfter(emi.getDueDate());

 
    Loan loan = loanRepository
            .findByLoanNumber(loanNumber)
            .orElseThrow(() ->
                    new IllegalStateException("Loan not found"));

 
    CustomerRiskProfile riskProfile =
            customerRiskProfileService.getOrCreate(loan.getCustomerId());

  
    if (isOverdue) {
        int overdueDays =
                (int) ChronoUnit.DAYS.between(emi.getDueDate(), today);

        riskProfile.setTotalOverdueEmis(
                riskProfile.getTotalOverdueEmis() + 1);

        riskProfile.setLastOverdueDate(today);

        if (overdueDays > riskProfile.getMaxOverdueDays()) {
            riskProfile.setMaxOverdueDays(overdueDays);
        }

       
        if (overdueDays > 60) {
            riskProfile.setRiskStatus(RiskStatus.HIGH);
        }
    }

   
    customerRiskProfileRepository.save(riskProfile);

   
    boolean allPaid =
            emiRepository.countUnpaidEmis(loanNumber) == 0;

    if (allPaid) {
        loan.setStatus("CLOSED");
        loanRepository.save(loan);

   
        riskProfile.setActiveLoans(
                Math.max(0, riskProfile.getActiveLoans() - 1));
        customerRiskProfileRepository.save(riskProfile);
    }

    
    auditService.logAction(
          
           AuditAction.PAY_EMI,
           EntityType.EMI,
            emi.getId()
    );
}


 
}

