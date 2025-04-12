package com.xpert.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Global exception handler for centralized error handling across all controllers.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.warn("EntityNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("Validation failed: {}", fieldErrors);
          
           Map<String, Object> errorResponse = new HashMap<>();
           errorResponse.put("timestamp", Instant.now());
           errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
           errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
           errorResponse.put("message", "Validation failed");
           errorResponse.put("path", request.getRequestURI());
           errorResponse.put("errors", fieldErrors);
           return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request.getRequestURI());
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, String path) {
        Map<String, Object> errorDetails = new HashMap<>();

        String errorId = UUID.randomUUID().toString(); //  Error ID for traceability
        log.debug("Generated error response with ID: {}", errorId); //  Trace it in logs

        errorDetails.put("timestamp", Instant.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        errorDetails.put("path", path);
        errorDetails.put("errorId", errorId); //  Include in API response

        return new ResponseEntity<>(errorDetails, status);
    }

}
