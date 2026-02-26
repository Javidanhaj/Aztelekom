package com.aztelekom.internship.service;

import com.aztelekom.internship.domain.entities.Category;
import com.aztelekom.internship.dto.category.CategoryUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(UUID id);

    Category createCategory(Category category);

    Category updateCategory(UUID id, CategoryUpdateRequest request);

    void deleteCategory(UUID id);
}
