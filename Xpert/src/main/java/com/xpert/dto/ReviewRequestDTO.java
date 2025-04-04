package com.xpert.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class ReviewRequestDTO {

    @NotNull(message = "Order ID is required")
    private Integer orderId;

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

    // Getters & Setters

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public UUID getClientId() { return clientId; }
    public void setClientId(UUID clientId) { this.clientId = clientId; }

    public UUID getXpertId() { return xpertId; }
    public void setXpertId(UUID xpertId) { this.xpertId = xpertId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }
}
