package com.example.productservice.service.impl;


import com.example.productservice.entity.Category;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("categoriesServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findCategoriesById(id);
        if(Objects.nonNull(category)){
            return category;
        }
        return null;
    }

    @Override
    public Category createCategory(String title) throws CategoryAlreadyExistsException {

        Category existingCategory = categoryRepository.findCategoriesByTitle(title);
        if(Objects.nonNull(existingCategory)){
            throw new CategoryAlreadyExistsException("Product with same title already exists");
        }
        Category category =
                Category.builder()
                        .title(title)
                .build();

        category.setCreatedAt(LocalDate.now());
        category.setIsDeleted("N");
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllByIsDeleted("N");
        if(CollectionUtils.isEmpty(categoryList)){
            return new ArrayList<>();
        }
        return categoryList;
    }

    @Override
    public Category getCategoryByTitle(String title) {
        Category category = categoryRepository.findCategoriesByTitleAndIsDeleted(title, "N");
        if(Objects.nonNull(category)){
            return category;
        }
        return null;
    }

    @Override
    public Category deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findCategoriesById(categoryId);
//        if(Objects.nonNull(category)){
//            return categoriesRepo.updateIsDeletedById(categoryId);
//        }
        return null;
    }
}
