package com.xpert.dto.service;

import com.xpert.enums.ServiceType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateServiceRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Expert user ID is required")
    private UUID expertId;

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
}
