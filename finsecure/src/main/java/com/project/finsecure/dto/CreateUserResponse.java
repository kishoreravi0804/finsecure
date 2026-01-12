package com.project.finsecure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserResponse {

    private boolean success;
    private String message;
    private String email;
    private String role;
    private String tempPassword; 
}
