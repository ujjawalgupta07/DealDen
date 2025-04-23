package com.example.productservice.builder;

import com.example.productservice.dto.response.CategoriesDTO;
import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    /**
     * Converts the Incoming Object to CategoriesResponseDTO
     *
     * @param category : Categories Entity Object
     * @return CategoriesResponseDTO
     */
    public CategoryResponseDTO convertToCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }

    public Category mapToCategory(CategoriesDTO categoriesDTO){
        Category category =
                Category.builder()
                        .title(categoriesDTO.getTitle())
                        .build();

        if(null != categoriesDTO.getId()){
            category.setId(Long.valueOf(categoriesDTO.getId()));
        }

        return category;
    }
}
