package com.project.finsecure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.finsecure.dto.CreateUserRequest;
import com.project.finsecure.dto.CreateUserResponse;
import com.project.finsecure.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }
}
