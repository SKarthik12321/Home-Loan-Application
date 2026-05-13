package com.homeloan.application.dto.loan;

import com.homeloan.application.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;

public record LoanStatusUpdateRequest(@NotNull LoanStatus status) {
}
