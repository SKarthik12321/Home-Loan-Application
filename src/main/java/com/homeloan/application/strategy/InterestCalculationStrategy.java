package com.homeloan.application.strategy;

import java.math.BigDecimal;

/**
 * Strategy pattern: swap EMI/pricing implementations without changing loan services.
 * Example alternate: risk-based pricing, promotional rates, fixed vs floating.
 */
public interface InterestCalculationStrategy {

    /**
     * Equated monthly installment for reducing balance (standard home-loan style formula).
     */
    BigDecimal monthlyEmi(BigDecimal principal, int tenureMonths);
}
