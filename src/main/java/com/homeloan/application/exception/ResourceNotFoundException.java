package com.homeloan.application.exception;

/**
 * Thrown when a requested entity does not exist (404-style semantics for APIs).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
