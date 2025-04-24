package com.xpert.dto.service;

import com.xpert.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer categoryId;
    private String categoryName;
    private String expertId;
    private String expertFullName;
    private ServiceType serviceType;
    private BigDecimal price;
    private Instant createdAt;
    private Instant updatedAt;
}
