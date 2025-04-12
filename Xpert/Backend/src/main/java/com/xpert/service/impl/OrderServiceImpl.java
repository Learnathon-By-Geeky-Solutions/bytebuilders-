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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of {@link OrderService}.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AgreementRepository agreementRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
        log.info("Creating new order with agreement ID: {}", dto.getAgreementId());

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
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order saved with ID: {}", savedOrder.getId());

        return modelMapper.map(savedOrder, OrderResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO getOrderById(UUID orderId) {
        log.info("Fetching order by ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        return modelMapper.map(order, OrderResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<OrderResponseDTO> getAllOrders(int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        log.info("Fetching orders with page = {}, size = {}", page, size);

        return orderRepository.findAll(PageRequest.of(page, size))
                .map(order -> modelMapper.map(order, OrderResponseDTO.class));
    }
}
