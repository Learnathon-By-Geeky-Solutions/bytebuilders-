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
     * Creates a new review based on the provided details.
     *
     * @param reviewRequest DTO containing review content, rating, etc.
     * @return A ResponseEntity with a confirmation message (String) upon success
     *
     * Note: Returns a String for simplicity since no ReviewDTO is returned.
     * If the system evolves to return a full ReviewDTO object,
     * this should be updated to maintain consistency across controllers.
     */
    @PostMapping
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequest) {
        reviewService.createReview(reviewRequest);
        return ResponseEntity.ok("Review submitted successfully!"); //  Clear success message
    }
}
