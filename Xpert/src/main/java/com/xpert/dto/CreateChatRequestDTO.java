package com.xpert.dto;

import com.xpert.enums.ChatType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateChatRequestDTO {

    @NotNull(message = "Chat type is required")
    private ChatType type;

    @NotEmpty(message = "At least one participant is required")
    private List<Long> participantIds;

    private String title;

    private Long orderId;

    // Getters and Setters
    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
