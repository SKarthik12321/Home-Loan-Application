package com.homeloan.application.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeloan.application.dto.loan.ApplicantRequest;
import com.homeloan.application.dto.loan.LoanApplicationCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test: full application context (H2 + security) validates DTO constraints end-to-end.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoanApplicationRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createLoan_invalidBody_returns400() throws Exception {
        LoanApplicationCreateRequest bad = new LoanApplicationCreateRequest(
                new ApplicantRequest("", "not-an-email", "123", BigDecimal.ONE),
                new BigDecimal("100"),
                6,
                "x",
                null);
        mockMvc.perform(post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors").exists());
    }
}
