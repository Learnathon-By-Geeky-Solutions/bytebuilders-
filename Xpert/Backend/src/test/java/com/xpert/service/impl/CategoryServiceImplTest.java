package com.xpert.service.impl;

import com.xpert.dto.category.CategoryDTO;
import com.xpert.dto.category.CreateCategoryRequestDTO;
import com.xpert.entity.Category;
import com.xpert.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        modelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
    }

    @Test
    void createCategory_withParentCategory_shouldSucceed() {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO();
        dto.setCategoryName("Subcategory");
        dto.setDescription("Child of Parent");
        dto.setImageUrl("image.jpg");
        dto.setParentCategoryId(1);

        Category parent = new Category();
        parent.setId(1);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryDTO result = categoryService.createCategory(dto);

        assertThat(result).isNotNull();
        assertThat(result.getCategoryName()).isEqualTo("Subcategory");
    }

    @Test
    void createCategory_withoutParentCategory_shouldSucceed() {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO();
        dto.setCategoryName("Root Category");
        dto.setDescription("Top level");
        dto.setImageUrl("root.jpg");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryDTO result = categoryService.createCategory(dto);

        assertThat(result).isNotNull();
        assertThat(result.getCategoryName()).isEqualTo("Root Category");
    }

    @Test
    void getAllCategories_shouldReturnList() {
        Category category = new Category();
        category.setCategoryName("General");

        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("General");
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        Category category = new Category();
        category.setId(99);
        category.setCategoryName("Electrician");

        when(categoryRepository.findById(99)).thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.getCategoryById(99);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(99);
        assertThat(result.getCategoryName()).isEqualTo("Electrician");
    }

    @Test
    void getCategoryById_notFound_shouldThrowException() {
        when(categoryRepository.findById(404)).thenReturn(Optional.empty());

        try {
            categoryService.getCategoryById(404);
        } catch (RuntimeException e) {
            assertThat(e).hasMessage("Category not found");
        }
    }
    
    @Test
    void mapToDTO_withSubcategories_shouldMapRecursively() {
        Category subCategory = new Category();
        subCategory.setId(2);
        subCategory.setCategoryName("Sub");

        Category root = new Category();
        root.setId(1);
        root.setCategoryName("Root");
        root.setSubcategories(List.of(subCategory));

        when(categoryRepository.findById(1)).thenReturn(Optional.of(root));

        CategoryDTO dto = categoryService.getCategoryById(1);

        assertThat(dto).isNotNull();
        assertThat(dto.getSubcategories()).isNotEmpty();
        assertThat(dto.getSubcategories().get(0).getCategoryName()).isEqualTo("Sub");
    }
    
    @Test
    void mapToDTO_withEmptySubcategoryList_shouldSkipRecursion() {
        Category category = new Category();
        category.setId(10);
        category.setCategoryName("Empty Category");
        category.setSubcategories(Collections.emptyList()); // important!

        when(categoryRepository.findById(10)).thenReturn(Optional.of(category));

        CategoryDTO dto = categoryService.getCategoryById(10);

        assertThat(dto).isNotNull();
        assertThat(dto.getSubcategories()).isEmpty(); // proves no recursive mapping
    }


}
