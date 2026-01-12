package com.project.finsecure.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;
    private String message;
    private LocalDateTime timestamp;
}
