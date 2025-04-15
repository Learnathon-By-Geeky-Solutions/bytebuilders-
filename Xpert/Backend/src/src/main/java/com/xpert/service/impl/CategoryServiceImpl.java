package com.xpert.service.impl;

import com.xpert.dto.category.CategoryDTO;
import com.xpert.dto.category.CreateCategoryRequestDTO;
import com.xpert.entity.Category;
import com.xpert.repository.CategoryRepository;
import com.xpert.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CreateCategoryRequestDTO dto) {
        // Automatically map basic fields from DTO to entity
        Category category = modelMapper.map(dto, Category.class);

        // Manually handle parent category if provided
        if (dto.getParentCategoryId() != null) {
            Optional<Category> parentOpt = categoryRepository.findById(dto.getParentCategoryId());
            parentOpt.ifPresent(category::setParentCategory);
        }

        Category saved = categoryRepository.save(category);
        return mapToDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToDTO)
                .toList(); 
    }


    @Override
    public CategoryDTO getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private CategoryDTO mapToDTO(Category category) {
        // Map basic fields
        CategoryDTO dto = modelMapper.map(category, CategoryDTO.class);

        // Set parentCategoryId manually if exists
        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getId());
        }

        // Recursively map subcategories
        if (category.getSubcategories() != null && !category.getSubcategories().isEmpty()) {
            dto.setSubcategories(
                category.getSubcategories().stream()
                    .map(this::mapToDTO)
                    .toList()
            );
        }


        return dto;
    }
}
