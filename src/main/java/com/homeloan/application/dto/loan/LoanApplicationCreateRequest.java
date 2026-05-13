package com.homeloan.application.dto.loan;

import com.homeloan.application.entity.LoanStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Create loan application: nested {@link ApplicantRequest} demonstrates {@code @Valid} cascading.
 */
public record LoanApplicationCreateRequest(
        @NotNull @Valid ApplicantRequest applicant,
        @NotNull
        @DecimalMin(value = "1000.0", message = "Minimum loan amount is 1000")
        BigDecimal requestedAmount,
        @NotNull
        @Min(value = 12, message = "Minimum tenure is 12 months")
        @Max(value = 360, message = "Maximum tenure is 360 months")
        Integer tenureMonths,
        @Size(max = 255) String purpose,
        LoanStatus status
) {
}
