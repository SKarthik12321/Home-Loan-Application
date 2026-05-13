package com.homeloan.application.controller.api;

import com.homeloan.application.config.OpenApiConfig;
import com.homeloan.application.dto.loan.LoanApplicationCreateRequest;
import com.homeloan.application.dto.loan.LoanApplicationResponse;
import com.homeloan.application.dto.loan.LoanStatusUpdateRequest;
import com.homeloan.application.service.LoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API for loan applications (JWT protected).
 */
@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Home loan application CRUD and workflow")
@SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
public class LoanApplicationRestController {

    private final LoanApplicationService loanApplicationService;

    @GetMapping
    @Operation(summary = "List all loan applications")
    public List<LoanApplicationResponse> list() {
        return loanApplicationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan application by id")
    public LoanApplicationResponse get(@PathVariable Long id) {
        return loanApplicationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a loan application (nested applicant validation)")
    public LoanApplicationResponse create(@Valid @RequestBody LoanApplicationCreateRequest request) {
        return loanApplicationService.create(request);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update loan status (admin only)")
    public LoanApplicationResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody LoanStatusUpdateRequest request) {
        return loanApplicationService.updateStatus(id, request);
    }
}
