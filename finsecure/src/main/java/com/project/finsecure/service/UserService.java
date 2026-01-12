package com.project.finsecure.service;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.finsecure.audit.AuditAction;
import com.project.finsecure.audit.EntityType;
import com.project.finsecure.dto.CreateUserRequest;
import com.project.finsecure.dto.CreateUserResponse;
import com.project.finsecure.entity.Users;
import com.project.finsecure.repository.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final AuditService auditService;

    public UserService(UserRepo userRepo,
                       BCryptPasswordEncoder encoder,
                       AuditService auditService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.auditService = auditService;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        String role = request.getRole().toUpperCase();
        if (!role.equals("ADMIN") && !role.equals("AGENT")) {
            throw new IllegalArgumentException("Role must be ADMIN or AGENT");
        }

        String tempPassword = generateTempPassword();

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setRole(role);
        user.setPassword(encoder.encode(tempPassword));
        user.setFirstLogin(true);
        user.setActive(true);

        Users saved = userRepo.save(user);

        
        auditService.logAction(
                
                AuditAction.CREATE_USER,
                EntityType.USER,
                saved.getId()
        );

       
        return new CreateUserResponse(
                true,
                "User created successfully",
                saved.getEmail(),
                saved.getRole(),
                tempPassword
        );
    }

    private String generateTempPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
