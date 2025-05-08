package com.example.productservice.service.impl;


import com.example.productservice.entity.Category;
import com.example.productservice.enums.DeletedStatus;
import com.example.commons.exception.CategoryAlreadyExistsException;
import com.example.commons.exception.CategoryNotFoundException;
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
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findCategoryById(id);
        if(Objects.nonNull(category)){
            return category;
        }

        throw new CategoryNotFoundException("Category with id : '" + id + "' not found.");
    }

    @Override
    public Category createCategory(String title) throws CategoryAlreadyExistsException {

        Category existingCategory = categoryRepository.findCategoryByTitle(title);
        if(Objects.nonNull(existingCategory)){
            throw new CategoryAlreadyExistsException("Category with same title " + title + " already exists.");
        }
        Category category = new Category();
        category.setTitle(title);

        category.setCreatedAt(LocalDate.now());
        category.setIsDeleted(DeletedStatus.N.getValue());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllByIsDeleted(DeletedStatus.N.getValue());
        if(CollectionUtils.isEmpty(categoryList)){
            return new ArrayList<>();
        }
        return categoryList;
    }

    @Override
    public Category getCategoryByTitle(String title) throws CategoryNotFoundException {
        Category category = categoryRepository.findCategoryByTitleAndIsDeleted(title, DeletedStatus.N.getValue());
        if(Objects.nonNull(category)){
            return category;
        }
        throw new CategoryNotFoundException("Category with title : '" + title + "' not found.");
    }

    @Override
    public Category deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId);
//        if(Objects.nonNull(category)){
//            return categoriesRepo.updateIsDeletedById(categoryId);
//        }
        return null;
    }
}
