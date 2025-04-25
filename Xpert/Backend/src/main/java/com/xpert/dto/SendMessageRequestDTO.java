package com.xpert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequestDTO {
    @NotNull(message = "Chat ID is required")
    private Long chatId;

    @NotNull(message = "Sender ID is required")
    private Long senderId;

    @NotBlank(message = "Message content is required")
    private String content;

    private List<String> attachmentUrls;
}
