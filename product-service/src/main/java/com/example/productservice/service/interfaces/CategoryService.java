package com.example.productservice.service.interfaces;

import com.example.productservice.entity.Category;
import com.example.commons.exception.CategoryAlreadyExistsException;
import com.example.commons.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id) throws CategoryNotFoundException;
    Category createCategory(String title) throws CategoryAlreadyExistsException;
    List<Category> getAllCategories();
    Category getCategoryByTitle(String title) throws CategoryNotFoundException;
    Category deleteCategoryById(Long productId);

}
