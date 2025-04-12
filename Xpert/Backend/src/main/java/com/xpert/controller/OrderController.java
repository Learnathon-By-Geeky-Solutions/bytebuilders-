package com.xpert.controller;

import com.xpert.dto.order.CreateOrderRequestDTO;
import com.xpert.dto.order.OrderResponseDTO;
import com.xpert.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing booking orders.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new booking order.
     *
     * @param dto the order request payload
     * @return the created order response
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderRequestDTO dto) {
        log.info("Received request to create order for agreementId: {}", dto.getAgreementId());
        OrderResponseDTO createdOrder = orderService.createOrder(dto);
        return ResponseEntity.ok(createdOrder);
    }

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId the UUID of the order
     * @return the order response
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID orderId) {
        log.info("Received request to fetch order with ID: {}", orderId);
        OrderResponseDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    /**
     * Retrieves all orders with pagination.
     *
     * @param page the page number (0-based)
     * @param size the number of items per page
     * @return a paginated list of order responses
     */
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to fetch all orders: page={}, size={}", page, size);
        Page<OrderResponseDTO> orders = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(orders);
    }
}
