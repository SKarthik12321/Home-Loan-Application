package com.homeloan.application.factory;

import com.homeloan.application.dto.loan.ApplicantRequest;
import com.homeloan.application.dto.loan.LoanApplicationCreateRequest;
import com.homeloan.application.entity.Applicant;
import com.homeloan.application.entity.LoanApplication;
import com.homeloan.application.entity.LoanStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Factory pattern: encapsulates construction of JPA entities from API DTOs.
 * Keeps controllers/services free of repetitive mapping logic and eases unit testing.
 */
@Component
public class LoanApplicationFactory {

    public Applicant newApplicant(ApplicantRequest request) {
        return Applicant.builder()
                .fullName(request.fullName().trim())
                .email(request.email().trim().toLowerCase())
                .phone(request.phone().trim())
                .annualIncome(request.annualIncome())
                .createdAt(Instant.now())
                .build();
    }

    public LoanApplication newLoanApplication(Applicant applicant, LoanApplicationCreateRequest request) {
        LoanStatus status = request.status() != null ? request.status() : LoanStatus.DRAFT;
        return LoanApplication.builder()
                .applicant(applicant)
                .requestedAmount(request.requestedAmount())
                .tenureMonths(request.tenureMonths())
                .status(status)
                .purpose(request.purpose())
                .createdAt(Instant.now())
                .build();
    }
}
