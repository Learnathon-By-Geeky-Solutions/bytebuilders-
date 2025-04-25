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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private AgreementRepository agreementRepository;
    private ModelMapper modelMapper;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        agreementRepository = mock(AgreementRepository.class);
        modelMapper = new ModelMapper();
        orderService = new OrderServiceImpl(orderRepository, userRepository, agreementRepository, modelMapper);
    }

    @Test
    void createOrder_shouldSucceed() {
        UUID agreementId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        Instant scheduledTime = Instant.now();

        CreateOrderRequestDTO dto = CreateOrderRequestDTO.builder()
                .agreementId(agreementId)
                .clientId(clientId)
                .xpertId(xpertId)
                .scheduledTime(scheduledTime)
                .build();

        Agreement agreement = new Agreement();
        Users client = new Users();
        Users xpert = new Users();

        when(agreementRepository.findById(agreementId)).thenReturn(Optional.of(agreement));
        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(xpert));

        Order savedOrder = Order.builder()
                .id(UUID.randomUUID())
                .agreement(agreement)
                .client(client)
                .xpert(xpert)
                .scheduledTime(scheduledTime)
                .status(OrderStatus.PLACED)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDTO result = orderService.createOrder(dto);
        assertThat(result).isNotNull();
    }

    @Test
    void createOrder_shouldThrowIfAgreementInvalid() {
        UUID invalidAgreementId = UUID.randomUUID();
        CreateOrderRequestDTO dto = CreateOrderRequestDTO.builder()
                .agreementId(invalidAgreementId)
                .clientId(UUID.randomUUID())
                .xpertId(UUID.randomUUID())
                .scheduledTime(Instant.now())
                .build();

        when(agreementRepository.findById(invalidAgreementId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid agreement ID");
    }

    @Test
    void createOrder_shouldThrowIfClientInvalid() {
        UUID agreementId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        CreateOrderRequestDTO dto = CreateOrderRequestDTO.builder()
                .agreementId(agreementId)
                .clientId(clientId)
                .xpertId(UUID.randomUUID())
                .scheduledTime(Instant.now())
                .build();

        when(agreementRepository.findById(agreementId)).thenReturn(Optional.of(new Agreement()));
        when(userRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid client ID");
    }

    @Test
    void createOrder_shouldThrowIfXpertInvalid() {
        UUID agreementId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();
        CreateOrderRequestDTO dto = CreateOrderRequestDTO.builder()
                .agreementId(agreementId)
                .clientId(clientId)
                .xpertId(xpertId)
                .scheduledTime(Instant.now())
                .build();

        when(agreementRepository.findById(agreementId)).thenReturn(Optional.of(new Agreement()));
        when(userRepository.findById(clientId)).thenReturn(Optional.of(new Users()));
        when(userRepository.findById(xpertId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid xpert ID");
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponseDTO result = orderService.getOrderById(orderId);
        assertThat(result).isNotNull();
    }

    @Test
    void getOrderById_shouldThrowIfNotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    void getAllOrders_shouldReturnPagedData() {
        Page<Order> page = new PageImpl<>(Collections.singletonList(new Order()));
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<OrderResponseDTO> result = orderService.getAllOrders(0, 5);
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getAllOrders_shouldDefaultPageSizeIfInvalid() {
        Page<Order> page = new PageImpl<>(Collections.singletonList(new Order()));
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<OrderResponseDTO> result = orderService.getAllOrders(-1, 0);
        assertThat(result.getContent()).hasSize(1);
    }
}
