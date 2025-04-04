package com.xpert.controller;

import com.xpert.dto.ReviewRequestDTO;
import com.xpert.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequest) {
        reviewService.createReview(reviewRequest);
        return ResponseEntity.ok("Review submitted successfully!");
    }
}
