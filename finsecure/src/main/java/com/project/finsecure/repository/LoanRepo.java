package com.project.finsecure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.finsecure.entity.Loan;

public interface LoanRepo extends JpaRepository<Loan, Long>{

    Optional<Loan> findByLoanNumber(String loanNumber);

    Optional<List<Loan>> findByCustomerId(String customerId);


   @Query(value = """
SELECT COALESCE(MAX(CAST(SUBSTRING(loan_number, 3) AS INTEGER)), 0)
FROM loan
""", nativeQuery = true)

    int findMaxLoanNumber();


                   
}
