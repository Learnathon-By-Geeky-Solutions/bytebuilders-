package com.xpert.service;

import com.xpert.dto.ReviewRequestDTO;
import com.xpert.entity.Order;
import com.xpert.entity.Review;
import com.xpert.entity.Users;
import com.xpert.repository.OrderRepository;
import com.xpert.repository.ReviewRepository;
import com.xpert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

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
        review.setIsApproved(dto.getIsApproved() != null ? dto.getIsApproved() : false);
        review.setCreatedAt(Instant.now());
        review.setUpdatedAt(Instant.now());

        reviewRepository.save(review);
    }
}
