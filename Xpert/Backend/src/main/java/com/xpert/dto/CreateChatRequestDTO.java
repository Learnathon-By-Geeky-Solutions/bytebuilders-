package com.xpert.dto;

import com.xpert.enums.ChatType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRequestDTO {
    @NotNull(message = "Chat type is required")
    private ChatType type;

    @NotEmpty(message = "At least one participant is required")
    private List<Long> participantIds;

    private String title;
    private Long orderId;
}
