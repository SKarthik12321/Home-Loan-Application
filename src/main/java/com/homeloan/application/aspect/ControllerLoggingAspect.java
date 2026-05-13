package com.homeloan.application.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP: logs controller entry/exit and execution time for observability in development.
 * <p>
 * Keeps controllers free of repetitive logging statements (cross-cutting concern).
 * </p>
 */
@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *) || "
            + "within(@org.springframework.stereotype.Controller *)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getSignature().toShortString();
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long took = System.currentTimeMillis() - start;
            log.debug("OK {} in {} ms", type, took);
            return result;
        } catch (Throwable ex) {
            long took = System.currentTimeMillis() - start;
            log.warn("FAIL {} in {} ms: {}", type, took, ex.getMessage());
            throw ex;
        }
    }
}
