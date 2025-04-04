package com.xpert.dto;

import com.xpert.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ChatMessageDTO {

    private Long messageId;
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;
    private MessageStatus status;
    private List<ChatAttachmentDTO> attachments;

    // Constructors
    public ChatMessageDTO() {}

    public ChatMessageDTO(Long messageId, Long chatId, Long senderId, String content,
                          LocalDateTime sentAt, MessageStatus status,
                          List<ChatAttachmentDTO> attachments) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
        this.status = status;
        this.attachments = attachments;
    }

    // Getters and Setters
    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public MessageStatus getStatus() { return status; }
    public void setStatus(MessageStatus status) { this.status = status; }

    public List<ChatAttachmentDTO> getAttachments() { return attachments; }
    public void setAttachments(List<ChatAttachmentDTO> attachments) { this.attachments = attachments; }
}
