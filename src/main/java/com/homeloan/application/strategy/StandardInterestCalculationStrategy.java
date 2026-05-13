package com.homeloan.application.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Default strategy: annual rate from configuration ({@code app.loan.default-annual-rate}).
 */
@Component
public class StandardInterestCalculationStrategy implements InterestCalculationStrategy {

    private final BigDecimal annualRatePercent;

    public StandardInterestCalculationStrategy(
            @Value("${app.loan.default-annual-rate}") BigDecimal annualRatePercent) {
        this.annualRatePercent = annualRatePercent;
    }

    @Override
    public BigDecimal monthlyEmi(BigDecimal principal, int tenureMonths) {
        if (tenureMonths <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal monthlyRate = annualRatePercent
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64)
                .divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
        }

        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal pow = onePlusR.pow(tenureMonths, MathContext.DECIMAL64);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(pow, MathContext.DECIMAL64);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
