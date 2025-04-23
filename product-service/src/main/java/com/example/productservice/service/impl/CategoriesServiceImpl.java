package com.example.productservice.service.impl;


import com.example.productservice.entity.Categories;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.repository.CategoriesRepo;
import com.example.productservice.service.interfaces.CategoriesService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("categoriesServiceImpl")
public class CategoriesServiceImpl implements CategoriesService {
    CategoriesRepo categoriesRepo;

    CategoriesServiceImpl(CategoriesRepo categoriesRepo){
        this.categoriesRepo = categoriesRepo;
    }

    @Override
    public Categories getCategoryById(Long id) {
        Categories category = categoriesRepo.findCategoriesById(id);
        if(Objects.nonNull(category)){
            return category;
        }
        return null;
    }

    @Override
    public Categories createCategories(String title) throws CategoryAlreadyExistsException {

        Categories existingCategory = categoriesRepo.findCategoriesByTitle(title);
        if(Objects.nonNull(existingCategory)){
            throw new CategoryAlreadyExistsException("Product with same title already exists");
        }
        Categories category =
                Categories.builder()
                        .title(title)
                .build();

        category.setCreatedAt(LocalDate.now());
        category.setIsDeleted("N");
        return categoriesRepo.save(category);
    }

    @Override
    public List<Categories> getAllCategories() {
        List<Categories> categoriesList = categoriesRepo.findAllByIsDeleted("N");
        if(CollectionUtils.isEmpty(categoriesList)){
            return new ArrayList<>();
        }
        return categoriesList;
    }

    @Override
    public Categories getCategoryByTitle(String title) {
        Categories category = categoriesRepo.findCategoriesByTitleAndIsDeleted(title, "N");
        if(Objects.nonNull(category)){
            return category;
        }
        return null;
    }

    @Override
    public Categories deleteCategoryById(Long categoryId) {
        Categories category = categoriesRepo.findCategoriesById(categoryId);
//        if(Objects.nonNull(category)){
//            return categoriesRepo.updateIsDeletedById(categoryId);
//        }
        return null;
    }
}
