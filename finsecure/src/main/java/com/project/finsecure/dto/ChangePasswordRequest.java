package com.project.finsecure.dto;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    private String email;
    private String newPassword;
    private String confirmPassword;

   
}
