package com.homeloan.application.dto.loan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Applicant details nested inside a loan creation request.
 */
public record ApplicantRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email String email,
        @NotBlank
        @Pattern(regexp = "^[0-9+\\-\\s]{7,20}$", message = "Phone must be 7-20 digits/plus/dash/space")
        String phone,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "Annual income must be positive")
        BigDecimal annualIncome
) {
}
