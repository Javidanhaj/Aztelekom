package com.aztelekom.internship.controller;

import com.aztelekom.internship.domain.entities.Category;
import com.aztelekom.internship.dto.category.CategoryCreateRequest;
import com.aztelekom.internship.dto.category.CategoryResponse;
import com.aztelekom.internship.dto.category.CategoryUpdateRequest;
import com.aztelekom.internship.mapper.CategoryMapper;
import com.aztelekom.internship.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponse> getAllcategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable UUID id){
        Category category = categoryService.getCategoryById(id);
        return categoryMapper.toResponse(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryCreateRequest request){
        Category category = categoryMapper.toEntity(request);
        Category createdCategory = categoryService.createCategory(category);
        return categoryMapper.toResponse(createdCategory);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable UUID id,
                                           @Valid @RequestBody CategoryUpdateRequest request) {
        Category updatedCategory = categoryService.updateCategory(id, request);
        return categoryMapper.toResponse(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}
