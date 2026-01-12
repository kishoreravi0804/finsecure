package com.project.finsecure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private boolean success;
    private String message;
    private boolean firstLogin;
    private String token;

   

   
}
