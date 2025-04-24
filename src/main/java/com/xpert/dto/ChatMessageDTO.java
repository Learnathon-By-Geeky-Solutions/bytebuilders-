package com.xpert.dto;

import com.xpert.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long messageId;
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;
    private MessageStatus status;
    private List<ChatAttachmentDTO> attachments;
}
