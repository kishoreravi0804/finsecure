package com.project.finsecure.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.finsecure.enums.RiskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_risk_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRiskProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;

    @Column(name = "total_loans", nullable = false)
    private int totalLoans;

    @Column(name = "active_loans", nullable = false)
    private int activeLoans;

    @Column(name = "total_overdue_emis", nullable = false)
    private int totalOverdueEmis;

    @Column(name = "total_bounce_count", nullable = false)
    private int totalBounceCount;

    @Column(name = "max_overdue_days", nullable = false)
    private int maxOverdueDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @Column(name = "last_overdue_date")
    private LocalDate lastOverdueDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
