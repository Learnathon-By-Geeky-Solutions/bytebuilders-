package com.xpert.dto;

import com.xpert.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long chatId;
    private ChatType type;
    private String title;
    private Long orderId;
    private List<Long> participantIds;
    private LocalDateTime lastMessageAt;
}
