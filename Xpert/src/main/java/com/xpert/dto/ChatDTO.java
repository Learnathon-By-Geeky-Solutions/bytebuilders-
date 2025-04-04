package com.xpert.dto;

import com.xpert.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;

public class ChatDTO {

    private Long chatId;
    private ChatType type;
    private String title;
    private Long orderId;
    private List<Long> participantIds;
    private LocalDateTime lastMessageAt;

    // Constructors
    public ChatDTO() {}

    public ChatDTO(Long chatId, ChatType type, String title, Long orderId, List<Long> participantIds, LocalDateTime lastMessageAt) {
        this.chatId = chatId;
        this.type = type;
        this.title = title;
        this.orderId = orderId;
        this.participantIds = participantIds;
        this.lastMessageAt = lastMessageAt;
    }

    // Getters and Setters
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
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

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }
}
