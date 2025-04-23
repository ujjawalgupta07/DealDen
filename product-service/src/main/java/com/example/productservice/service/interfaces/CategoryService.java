package com.example.productservice.service.interfaces;


import com.example.productservice.entity.Category;
import com.example.productservice.exception.CategoryAlreadyExistsException;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);
    Category createCategory(String title) throws CategoryAlreadyExistsException;
    List<Category> getAllCategories();
    Category getCategoryByTitle(String title);
    Category deleteCategoryById(Long productId);

}
