package com.xpert.controller;

import com.xpert.dto.payment.CreatePaymentRequestDTO;
import com.xpert.dto.payment.PaymentResponseDTO;
import com.xpert.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for handling payment-related endpoints.
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payments", description = "APIs for creating and retrieving payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Creates a new payment.
     *
     * @param dto the payment request payload
     * @return created payment response
     */
    @PostMapping
    @Operation(summary = "Create a payment", description = "Creates a new payment record for an order")
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody CreatePaymentRequestDTO dto) {
    	log.info("POST /api/payments - Request received for order: {}", dto.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(dto));
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param id the UUID of the payment
     * @return the payment response
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieves the details of a specific payment by UUID")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable @NotNull UUID id) {
        log.info("GET /api/payments/{}", id);
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}
