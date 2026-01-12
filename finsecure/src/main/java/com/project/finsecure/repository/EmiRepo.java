package com.project.finsecure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.finsecure.entity.Emi;

public interface EmiRepo extends JpaRepository<Emi, Long> {

    
    Optional<Emi> findByLoanNumberAndEmiNumber(
            String loanNumber,
            int emiNumber
    );

   
    List<Emi> findByLoanNumber(String loanNumber);

  
    List<Emi> findByLoanNumberAndPaidFalse(String loanNumber);

   
@Query("""
    SELECT COUNT(e)
    FROM Emi e
    WHERE e.loanNumber = :loanNumber
    AND e.paid = false
""")
long countUnpaidEmis(@Param("loanNumber") String loanNumber);

}
