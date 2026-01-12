package com.project.finsecure.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.finsecure.audit.AuditAction;
import com.project.finsecure.audit.EntityType;
import com.project.finsecure.entity.AuditLog;
import com.project.finsecure.repository.AuditRepo;

@Service
public class AuditService {

    private final AuditRepo auditRepo;

    public AuditService(AuditRepo auditRepo) {
        this.auditRepo = auditRepo;
    }

    
    public void logAction(
            AuditAction action,
            EntityType entityType,
            Long entityId) {

       
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return; 
        }

        String userEmail = authentication.getName();

        AuditLog auditLog = new AuditLog();
        auditLog.setUserEmail(userEmail);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);

       
        auditRepo.save(auditLog);
    }
}
