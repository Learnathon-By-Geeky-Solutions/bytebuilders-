package com.xpert.service.impl;

import com.xpert.dto.review.ReviewRequestDTO;
import com.xpert.entity.Order;
import com.xpert.entity.Review;
import com.xpert.entity.Users;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.ReviewRepository;
import com.xpert.repository.UserRepository;
import com.xpert.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    public void createReview(ReviewRequestDTO dto) {
        // Validate foreign keys
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + dto.getOrderId()));

        Users client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + dto.getClientId()));

        Users xpert = userRepository.findById(dto.getXpertId())
                .orElseThrow(() -> new IllegalArgumentException("Xpert not found with ID: " + dto.getXpertId()));

        // Optional: prevent duplicate reviews
        boolean alreadyReviewed = reviewRepository.existsByOrderAndClient(order, client);
        if (alreadyReviewed) {
            throw new IllegalStateException("You have already submitted a review for this order.");
        }

        // Create and save review
        Review review = new Review();
        review.setOrder(order);
        review.setClient(client);
        review.setXpert(xpert);
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());
        review.setIsApproved(Boolean.TRUE.equals(dto.getIsApproved()));
        review.setCreatedAt(Instant.now());
        review.setUpdatedAt(Instant.now());

        reviewRepository.save(review);
    }
}
