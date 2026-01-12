package com.project.finsecure.dto;

import lombok.Getter;

@Getter
public class GenericResponse {

    private boolean success;
    private String message;

    public GenericResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

  
}
