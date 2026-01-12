package com.project.finsecure.controller;

import com.project.finsecure.dto.*;
import com.project.finsecure.service.AuthService;

import org.springframework.web.bind.annotation.*;

import com.project.finsecure.service.AdminService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AdminService adminService;

    public AuthController(AuthService authService,AdminService adminService) {
        this.authService = authService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
  
    @PostMapping("/change-password")
    public GenericResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return authService.changePassword(request);
    }
}
