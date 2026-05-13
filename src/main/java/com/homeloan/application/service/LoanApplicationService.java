package com.homeloan.application.service;

import com.homeloan.application.dto.loan.LoanApplicationCreateRequest;
import com.homeloan.application.dto.loan.LoanApplicationResponse;
import com.homeloan.application.dto.loan.LoanStatusUpdateRequest;
import com.homeloan.application.entity.Applicant;
import com.homeloan.application.entity.LoanApplication;
import com.homeloan.application.entity.LoanStatus;
import com.homeloan.application.exception.BusinessRuleException;
import com.homeloan.application.exception.ResourceNotFoundException;
import com.homeloan.application.factory.LoanApplicationFactory;
import com.homeloan.application.repository.ApplicantRepository;
import com.homeloan.application.repository.LoanApplicationRepository;
import com.homeloan.application.strategy.InterestCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Application service for loan use-cases. Delegates entity construction to {@link LoanApplicationFactory}
 * and pricing to an {@link InterestCalculationStrategy} (strategy pattern).
 */
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final ApplicantRepository applicantRepository;
    private final LoanApplicationFactory loanApplicationFactory;
    private final InterestCalculationStrategy interestCalculationStrategy;

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAll() {
        return loanApplicationRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        return loanApplicationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found: " + id));
    }

    @Transactional
    public LoanApplicationResponse create(LoanApplicationCreateRequest request) {
        Applicant applicant = applicantRepository
                .findByEmailIgnoreCase(request.applicant().email().trim().toLowerCase())
                .orElseGet(() -> applicantRepository.save(loanApplicationFactory.newApplicant(request.applicant())));

        LoanApplication loan = loanApplicationFactory.newLoanApplication(applicant, request);
        LoanApplication saved = loanApplicationRepository.save(loan);
        return toResponse(saved);
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, LoanStatusUpdateRequest request) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found: " + id));
        assertTransition(loan.getStatus(), request.status());
        loan.setStatus(request.status());
        return toResponse(loanApplicationRepository.save(loan));
    }

    private void assertTransition(LoanStatus current, LoanStatus next) {
        if (current == LoanStatus.APPROVED && next != LoanStatus.APPROVED) {
            throw new BusinessRuleException("Approved applications cannot change status");
        }
        if (current == LoanStatus.REJECTED && next != LoanStatus.REJECTED) {
            throw new BusinessRuleException("Rejected applications cannot change status");
        }
    }

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        Applicant a = loan.getApplicant();
        BigDecimal emi = interestCalculationStrategy.monthlyEmi(loan.getRequestedAmount(), loan.getTenureMonths());
        return new LoanApplicationResponse(
                loan.getId(),
                a.getId(),
                a.getFullName(),
                a.getEmail(),
                loan.getRequestedAmount(),
                loan.getTenureMonths(),
                loan.getStatus(),
                loan.getPurpose(),
                loan.getCreatedAt(),
                emi);
    }
}
