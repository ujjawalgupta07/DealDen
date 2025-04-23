package com.example.productservice.builder;

import com.example.productservice.dto.response.CategoriesDTO;
import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    /**
     * Converts the Incoming Object to CategoriesResponseDTO
     *
     * @param category : Categories Entity Object
     * @return CategoriesResponseDTO
     */
    public static CategoryResponseDTO convertToCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }

    public static Category mapToCategory(CategoriesDTO categoriesDTO){
        Category category =
                Category.builder()
                        .title(categoriesDTO.getTitle())
                        .build();

        if(null != categoriesDTO.getId()){
            category.setId(Long.valueOf(categoriesDTO.getId()));
        }

        return category;
    }

    public static List<CategoryResponseDTO> convertToDTOList(List<Category> categories){
        return
                categories
                        .stream()
                        .map(CategoryMapper::convertToCategoryResponseDTO)
                        .collect(Collectors.toList());
    }

}
