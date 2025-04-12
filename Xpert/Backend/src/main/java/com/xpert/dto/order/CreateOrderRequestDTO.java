package com.xpert.dto.order;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateOrderRequestDTO {
    private UUID agreementId;
    private UUID clientId;
    private UUID xpertId;
    private Instant scheduledTime;
}
