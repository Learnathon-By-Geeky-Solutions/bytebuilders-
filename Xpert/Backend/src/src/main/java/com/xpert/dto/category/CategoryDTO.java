package com.xpert.dto.category;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Integer id;
    private String categoryName;
    private String imageUrl;
    private String description;
    private Integer parentCategoryId;
    private List<CategoryDTO> subcategories;
}
