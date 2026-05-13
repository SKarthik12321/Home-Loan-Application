package com.homeloan.application.bootstrap;

import com.homeloan.application.dto.loan.ApplicantRequest;
import com.homeloan.application.dto.loan.LoanApplicationCreateRequest;
import com.homeloan.application.entity.LoanStatus;
import com.homeloan.application.entity.UserAccount;
import com.homeloan.application.repository.LoanApplicationRepository;
import com.homeloan.application.repository.UserAccountRepository;
import com.homeloan.application.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Seeds a demo operator account and a few loan applications for Swagger/manual testing.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SampleDataLoader implements ApplicationRunner {

    private final UserAccountRepository userAccountRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationService loanApplicationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedUserIfMissing("demo", "demo-pass-123", "USER");
        seedUserIfMissing("admin", "admin-pass-123", "ADMIN");

        if (loanApplicationRepository.count() == 0) {
            log.info("Loading sample loan applications");
            loanApplicationService.create(sample("Alice Johnson", "alice@example.com", "+1 555-0101",
                    new BigDecimal("95000"), 240, LoanStatus.SUBMITTED, "Primary residence"));
            loanApplicationService.create(sample("Bob Singh", "bob@example.com", "+1 555-0102",
                    new BigDecimal("120000"), 300, LoanStatus.UNDER_REVIEW, "Refinance"));
            loanApplicationService.create(sample("Priya Rao", "priya@example.com", "+91 90000 12345",
                    new BigDecimal("75000"), 180, LoanStatus.APPROVED, "First-time buyer"));
        }
    }

    private void seedUserIfMissing(String username, String rawPassword, String role) {
        if (userAccountRepository.existsByUsernameIgnoreCase(username)) {
            return;
        }
        userAccountRepository.save(UserAccount.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(role)
                .build());
        log.info("Seeded user '{}'", username);
    }

    private LoanApplicationCreateRequest sample(
            String name,
            String email,
            String phone,
            BigDecimal amount,
            int tenure,
            LoanStatus status,
            String purpose) {
        return new LoanApplicationCreateRequest(
                new ApplicantRequest(name, email, phone, new BigDecimal("105000")),
                amount,
                tenure,
                purpose,
                status);
    }
}
