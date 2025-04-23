package com.example.productservice.service.interfaces;


import com.example.productservice.entity.Categories;
import com.example.productservice.exception.CategoryAlreadyExistsException;

import java.util.List;

public interface CategoriesService {

    Categories getCategoryById(Long id);
    Categories createCategories(String title) throws CategoryAlreadyExistsException;
    List<Categories> getAllCategories();
    Categories getCategoryByTitle(String title);
    Categories deleteCategoryById(Long productId);

}
