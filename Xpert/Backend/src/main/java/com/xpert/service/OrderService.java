package com.xpert.service;

import com.xpert.dto.order.CreateOrderRequestDTO;
import com.xpert.dto.order.OrderResponseDTO;

import java.util.List;


/**
 * Service interface for managing booking orders.
 */
public interface OrderService {

    /**
     * Creates a new booking order.
     *
     * @param dto the request data to create the order
     * @return the created order response
     */
    OrderResponseDTO createOrder(CreateOrderRequestDTO dto);

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all order responses
     */
    List<OrderResponseDTO> getAllOrders();

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId the ID of the order
     * @return the order response
     */
    OrderResponseDTO getOrderById(Integer orderId);
}
