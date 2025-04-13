package com.xpert.controller;

import com.xpert.dto.review.ReviewRequestDTO;
import com.xpert.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling expert reviews.
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor //  Promotes immutability and testability
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Endpoint to create a new review for a completed order.
     *
     * @param reviewRequest the review request data
     * @return success message
     */
    @PostMapping
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequest) {
        reviewService.createReview(reviewRequest);
        return ResponseEntity.ok("Review submitted successfully!"); //  Clear success message
    }
}
