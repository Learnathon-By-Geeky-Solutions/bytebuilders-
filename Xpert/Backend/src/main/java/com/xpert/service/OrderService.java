package com.xpert.service;

import com.xpert.dto.order.CreateOrderRequestDTO;
import com.xpert.dto.order.OrderResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing booking orders.
 */
public interface OrderService {

    /**
     * Creates a new booking order.
     *
     * @param dto the request data to create the order
     * @return the created order response
     * @throws IllegalArgumentException if dto contains invalid data
     * @throws ServiceException if order creation fails due to system error
     */
    OrderResponseDTO createOrder(CreateOrderRequestDTO dto);

    /**
     * Retrieves all orders in the system with pagination.
     *
     * @param page the page number (zero-based)
     * @param size the page size
     * @return a paginated list of order responses
     */
    Page<OrderResponseDTO> getAllOrders(int page, int size);

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId the ID of the order
     * @return the order response
     * @throws EntityNotFoundException if no order exists with the given ID
     */
    OrderResponseDTO getOrderById(UUID orderId);
}
