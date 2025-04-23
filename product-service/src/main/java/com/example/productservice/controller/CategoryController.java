package com.example.productservice.controller;

import com.example.productservice.builder.CategoryMapper;
import com.example.productservice.dto.request.CreateCategoryRequestDTO;
import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.entity.Category;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidCategoryIdException;
import com.example.productservice.service.interfaces.CategoryService;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CategoryController {

    CategoryService categoryService;
    CategoryMapper categoryMapper;

    public CategoryController(@Qualifier("categoriesServiceImpl") CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping("/category")
    public CategoryResponseDTO createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO)
            throws BadRequestException, CategoryAlreadyExistsException {

        validateCreateCategoryRequest(createCategoryRequestDTO);
        Category category = categoryService.createCategory(createCategoryRequestDTO.getTitle());

        return categoryMapper.convertToCategoryResponseDTO(category);
    }

    private void validateCreateCategoryRequest(CreateCategoryRequestDTO createCategoryRequestDTO) throws BadRequestException {
        if(StringUtils.isBlank(createCategoryRequestDTO.getTitle())) {
            throw new BadRequestException("Category Title is missing");
        }
    }

    @GetMapping("/categories")
    public List<CategoryResponseDTO> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategories();

        if (CollectionUtils.isEmpty(categoryList)) {
            return null;
        }

        List<CategoryResponseDTO> response = new ArrayList<>();
        for (Category category : categoryList) {
            response.add(categoryMapper.convertToCategoryResponseDTO(category));
        }

        return response;
    }

    @GetMapping("/category/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable("id") String categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Category Id.");
        }
        Category category = categoryService.getCategoryById(Long.valueOf(categoryId));

        if(Objects.isNull(category)){
            throw new CategoryNotFoundException("Category not found.");
        }
        return categoryMapper.convertToCategoryResponseDTO(category);
    }

    @DeleteMapping("/category/{id}")
    public CategoryResponseDTO deleteCategoryById(@PathVariable("id") Long categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Product Id.");
        }
        Category category = categoryService.deleteCategoryById(categoryId);

        if(Objects.isNull(category)){
            throw new CategoryNotFoundException("Category not found.");
        }
        return categoryMapper.convertToCategoryResponseDTO(category);
    }
}
