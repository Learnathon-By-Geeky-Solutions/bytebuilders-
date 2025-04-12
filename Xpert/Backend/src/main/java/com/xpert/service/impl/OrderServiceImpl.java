package com.xpert.service.impl;

import com.xpert.dto.order.CreateOrderRequestDTO;
import com.xpert.dto.order.OrderResponseDTO;
import com.xpert.entity.Agreement;
import com.xpert.entity.Order;
import com.xpert.entity.Users;
import com.xpert.enums.OrderStatus;
import com.xpert.repository.AgreementRepository;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.UserRepository;
import com.xpert.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Service implementation for managing booking orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
        Agreement agreement = agreementRepository.findById(dto.getAgreementId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid agreement ID"));

        Users client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));

        Users xpert = userRepository.findById(dto.getXpertId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid xpert ID"));

        Order order = Order.builder()
                .agreement(agreement)
                .client(client)
                .xpert(xpert)
                .scheduledTime(dto.getScheduledTime())
                .status(OrderStatus.PLACED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponseDTO.class);
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size))
                .map(order -> modelMapper.map(order, OrderResponseDTO.class));
    }

    @Override
    public OrderResponseDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
