package com.homeloan.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the monolithic Home Loan Application.
 * <p>
 * Layers: web (REST + Thymeleaf) → service → repository → JPA entities.
 * Cross-cutting: security (JWT), validation, global exception handling, and AOP logging.
 * </p>
 */
@SpringBootApplication
public class HomeLoanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeLoanApplication.class, args);
    }
}
