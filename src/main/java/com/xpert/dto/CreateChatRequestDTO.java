package com.xpert.dto;

import com.xpert.enums.ChatType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateChatRequestDTO {
    @NotNull(message = "Chat type is required")
    private ChatType type;

    @NotEmpty(message = "At least one participant is required")
    private List<Long> participantIds;

    private String title;
    private Long orderId;
}
