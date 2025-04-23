package com.example.productservice.builder;

import com.example.productservice.dto.response.CategoriesDTO;
import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.entity.Categories;
import org.springframework.stereotype.Component;

@Component
public class CategoriesMapper {

    /**
     * Converts the Incoming Object to CategoriesResponseDTO
     *
     * @param categories : Categories Entity Object
     * @return CategoriesResponseDTO
     */
    public CategoryResponseDTO convertToCategoryResponseDTO(Categories categories) {
        return CategoryResponseDTO.builder()
                .id(categories.getId())
                .title(categories.getTitle())
                .build();
    }

    public Categories mapToCategory(CategoriesDTO categoriesDTO){
        Categories categories =
                Categories.builder()
                        .title(categoriesDTO.getTitle())
                        .build();

        if(null != categoriesDTO.getId()){
            categories.setId(Long.valueOf(categoriesDTO.getId()));
        }

        return categories;
    }
}
