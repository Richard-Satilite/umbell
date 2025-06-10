package com.umbell.repository;

import com.umbell.models.Category;
import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    Category findById(Long id);
    List<Category> findAll();
    void update(Category category);
    void delete(Long id);
} 