package com.umbell.service;

import com.umbell.models.Category;
import com.umbell.repository.CategoryRepository;
import com.umbell.repository.CategoryRepositoryImpl;

import java.util.List;

public abstract class CategoryService {
    protected final CategoryRepository categoryRepository;

    public CategoryService() {
        this.categoryRepository = new CategoryRepositoryImpl();
    }

    public Category createCategory(Category category) {
        // Add common validation logic here if needed
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Category category) {
        // Add common validation logic here if needed
        categoryRepository.update(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
} 