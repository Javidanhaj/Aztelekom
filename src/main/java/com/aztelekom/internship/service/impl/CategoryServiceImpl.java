package com.aztelekom.internship.service.impl;

import com.aztelekom.internship.domain.entities.Category;
import com.aztelekom.internship.dto.category.CategoryUpdateRequest;
import com.aztelekom.internship.repository.CategoryRepository;
import com.aztelekom.internship.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
        return category;
    }

    @Override
    public Category createCategory(Category category) {

        String newName = category.getName();
        String name = normalize(newName);

        category.setName(name);

        if(categoryRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists with name: " + name);
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, CategoryUpdateRequest request) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        String newName = normalize(request.name());

        if (existing.getName().equalsIgnoreCase(newName)) {
            existing.setName(newName);
            return categoryRepository.save(existing);
        }

        if (categoryRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException("Category already exists with name: " + newName);
        }

        existing.setName(newName);
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(UUID id) {
        if(!categoryRepository.existsById(id)){
            throw new EntityNotFoundException("Category not found!");
        }
        categoryRepository.deleteById(id);
    }

    private String normalize(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name cannot be null or blank!");
        }
        return name.trim().replaceAll("\\s+", " ");
    }
}
