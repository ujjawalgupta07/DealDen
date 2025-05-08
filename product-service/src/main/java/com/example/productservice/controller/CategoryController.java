package com.example.productservice.controller;

import com.example.commons.exception.*;
import com.example.commons.aop.annotations.HasAnyRole;
import com.example.productservice.builder.CategoryMapper;
import com.example.productservice.dto.request.CreateCategoryRequestDTO;
import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.entity.Category;
import com.example.productservice.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class.getName());

    public CategoryController(@Qualifier("categoriesServiceImpl") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestHeader("X-User-Name") String username,
                                                              @RequestHeader("X-User-Roles") String roles,
                                                              @Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO)
            throws CategoryAlreadyExistsException {

        System.out.println("USername ===> :: " + username);
        System.out.println("Roles ===> :: " + roles);
        LOGGER.info("Creating category with title : {} ", createCategoryRequestDTO.getTitle());
        Category category = categoryService.createCategory(createCategoryRequestDTO.getTitle());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryMapper.convertToCategoryResponseDTO(category));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){

        LOGGER.info("Getting all categories");
        List<Category> categoryList = categoryService.getAllCategories();

        if (CollectionUtils.isEmpty(categoryList)) {
            return null;
        }

        return ResponseEntity.ok(CategoryMapper.convertToDTOList(categoryList));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") Long categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        LOGGER.info("Fetching category with id : {}", categoryId);
        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Category Id.");
        }
        Category category = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(CategoryMapper.convertToCategoryResponseDTO(category));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public CategoryResponseDTO deleteCategoryById(@PathVariable("id") Long categoryId)
            throws InvalidCategoryIdException, CategoryNotFoundException {

        if(null == categoryId){
            throw new InvalidCategoryIdException("Invalid Product Id.");
        }
        Category category = categoryService.deleteCategoryById(categoryId);

        return CategoryMapper.convertToCategoryResponseDTO(category);
    }
}
