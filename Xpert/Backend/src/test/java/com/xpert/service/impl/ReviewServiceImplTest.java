package com.xpert.service.impl;

import com.xpert.dto.review.ReviewRequestDTO;
import com.xpert.entity.Order;
import com.xpert.entity.Review;
import com.xpert.entity.Users;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.ReviewRepository;
import com.xpert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        reviewService = new ReviewServiceImpl(reviewRepository, orderRepository, userRepository);
    }

    @Test
    void createReview_shouldSucceed() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();

        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .orderId(orderId)
                .clientId(clientId)
                .xpertId(xpertId)
                .rating(5)
                .reviewText("Excellent work!")
                .isApproved(true)
                .build();

        Order order = new Order();
        Users client = new Users();
        Users xpert = new Users();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(xpert));
        when(reviewRepository.existsByOrderAndClient(order, client)).thenReturn(false);

        reviewService.createReview(dto);

        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createReview_shouldThrowIfOrderNotFound() {
        UUID orderId = UUID.randomUUID();
        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .orderId(orderId)
                .clientId(UUID.randomUUID())
                .xpertId(UUID.randomUUID())
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    void createReview_shouldThrowIfClientNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();

        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .orderId(orderId)
                .clientId(clientId)
                .xpertId(xpertId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(userRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Client not found");
    }

    @Test
    void createReview_shouldThrowIfXpertNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();

        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .orderId(orderId)
                .clientId(clientId)
                .xpertId(xpertId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(userRepository.findById(clientId)).thenReturn(Optional.of(new Users()));
        when(userRepository.findById(xpertId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Xpert not found");
    }

    @Test
    void createReview_shouldThrowIfAlreadyReviewed() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID xpertId = UUID.randomUUID();

        Order order = new Order();
        Users client = new Users();
        Users xpert = new Users();

        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .orderId(orderId)
                .clientId(clientId)
                .xpertId(xpertId)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(xpertId)).thenReturn(Optional.of(xpert));
        when(reviewRepository.existsByOrderAndClient(order, client)).thenReturn(true);

        assertThatThrownBy(() -> reviewService.createReview(dto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already submitted a review");
    }
}
