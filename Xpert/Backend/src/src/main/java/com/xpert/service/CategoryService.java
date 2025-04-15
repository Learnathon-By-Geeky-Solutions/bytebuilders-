package com.xpert.service;

import com.xpert.dto.category.CategoryDTO;
import com.xpert.dto.category.CreateCategoryRequestDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CreateCategoryRequestDTO dto);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Integer id);
}
