package com.xpert.controller;

import com.xpert.dto.category.CategoryDTO;
import com.xpert.dto.category.CreateCategoryRequestDTO;
import com.xpert.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor //  Enables constructor injection for final fields
public class CategoryController {

	private final CategoryService categoryService; //  Constructor injection (no @Autowired)

    // Only Admins can create a category
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequestDTO dto) {
        CategoryDTO created = categoryService.createCategory(dto);
        return ResponseEntity.ok(created);
    }

    //  Public - Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //  Public - Get single category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
