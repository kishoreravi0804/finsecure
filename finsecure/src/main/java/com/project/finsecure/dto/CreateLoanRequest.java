package com.project.finsecure.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoanRequest {

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotNull(message = "Loan amount is required")
    @Min(value = 10_000, message = "Minimum loan amount is 10,000")
    @Max(value = 20_00_000, message = "Maximum loan amount is 20,00,000")
    private Long requestedAmount;

    @Min(value = 300, message = "Minimum CIBIL score is 300")
    @Max(value = 900, message = "Maximum CIBIL score is 900")
    private int cibilScore;

    @Min(value = 1, message = "Tenure must be at least 1 month")
    private int tenureMonths;
}
