package com.homeloan.application.dto.loan;

import com.homeloan.application.entity.LoanStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanApplicationResponse(
        Long id,
        Long applicantId,
        String applicantName,
        String applicantEmail,
        BigDecimal requestedAmount,
        Integer tenureMonths,
        LoanStatus status,
        String purpose,
        Instant createdAt,
        BigDecimal indicativeMonthlyEmi
) {
}
