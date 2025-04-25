package com.xpert.dto.review;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReviewRequestDTO {

    @NotNull(message = "Order ID is required")
    private UUID orderId;

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    @NotNull(message = "Xpert ID is required")
    private UUID xpertId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    private String reviewText;

    private Boolean isApproved = false;
}
