package com.xpert.exception;

/**
 * Custom exception for encryption-related failures.
 */
public class EncryptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
