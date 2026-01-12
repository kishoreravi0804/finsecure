package com.project.finsecure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.finsecure.entity.CustomerRiskProfile;
import com.project.finsecure.enums.RiskStatus;
import com.project.finsecure.repository.CustomerRiskProfileRepository;

@Service
public class CustomerRiskProfileService {

    private final CustomerRiskProfileRepository riskProfileRepository;

    public CustomerRiskProfileService(CustomerRiskProfileRepository riskProfileRepository) {
        this.riskProfileRepository = riskProfileRepository;
    }

    @Transactional
    public CustomerRiskProfile getOrCreate(String customerId) {

        return riskProfileRepository.findByCustomerId(customerId)
                .orElseGet(() -> {

                    CustomerRiskProfile profile = new CustomerRiskProfile();
                    profile.setCustomerId(customerId);

                  
                    profile.setTotalLoans(0);
                    profile.setActiveLoans(0);
                    profile.setTotalOverdueEmis(0);
                    profile.setTotalBounceCount(0);
                    profile.setMaxOverdueDays(0);
                    profile.setRiskStatus(RiskStatus.LOW);
                    profile.setLastOverdueDate(null);

                    return riskProfileRepository.save(profile);
                });
    }
}
