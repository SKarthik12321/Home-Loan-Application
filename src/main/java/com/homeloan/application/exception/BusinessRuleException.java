package com.homeloan.application.exception;

/**
 * Domain rule violation (e.g. invalid state transition) mapped to 400 by the global handler.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
