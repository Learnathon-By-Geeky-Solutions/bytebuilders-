package com.xpert.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    @Size(max = 30)
    private String categoryName;

    private String imageUrl;
    private String description;
    private Integer parentCategoryId;
}
