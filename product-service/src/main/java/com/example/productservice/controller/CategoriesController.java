package com.example.productservice.controller;

import com.example.productservice.builder.CategoriesMapper;
import com.example.productservice.dto.request.CreateCategoryRequestDTO;
import com.example.productservice.dto.response.CategoriesResponseDTO;
import com.example.productservice.entity.Categories;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidCategoryIdException;
import com.example.productservice.service.interfaces.CategoriesService;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CategoriesController {

    CategoriesService categoriesService;
    CategoriesMapper categoriesMapper;

    public CategoriesController(@Qualifier("categoriesServiceImpl") CategoriesService categoriesService, CategoriesMapper categoriesMapper) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
    }

    @PostMapping("/category")
    public CategoriesResponseDTO createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO)
            throws BadRequestException, CategoryAlreadyExistsException {

        validateCreateCategoryRequest(createCategoryRequestDTO);
        Categories category = categoriesService.createCategories(createCategoryRequestDTO.getTitle());

        return categoriesMapper.convertToCategoryResponseDTO(category);
    }

    private void validateCreateCategoryRequest(CreateCategoryRequestDTO createCategoryRequestDTO) throws BadRequestException {
        if(StringUtils.isBlank(createCategoryRequestDTO.getTitle())) {
            throw new BadRequestException("Category Title is missing");
        }
    }

    @GetMapping("/categories")
    public List<CategoriesResponseDTO> getAllCategories(){
        List<Categories> categoriesList = categoriesService.getAllCategories();

        if (CollectionUtils.isEmpty(categoriesList)) {
            return null;
        }

        List<CategoriesResponseDTO> response = new ArrayList<>();
        for (Categories category : categoriesList) {
            response.add(categoriesMapper.convertToCategoryResponseDTO(category));
        }

        return response;
    }

    @GetMapping("/category/{id}")
    public CategoriesResponseDTO getCategoryById(@PathVariable("id") String categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Category Id.");
        }
        Categories category = categoriesService.getCategoryById(Long.valueOf(categoryId));

        if(Objects.isNull(category)){
            throw new CategoryNotFoundException("Category not found.");
        }
        return categoriesMapper.convertToCategoryResponseDTO(category);
    }

    @DeleteMapping("/category/{id}")
    public CategoriesResponseDTO deleteCategoryById(@PathVariable("id") Long categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Product Id.");
        }
        Categories category = categoriesService.deleteCategoryById(categoryId);

        if(Objects.isNull(category)){
            throw new CategoryNotFoundException("Category not found.");
        }
        return categoriesMapper.convertToCategoryResponseDTO(category);
    }
}
