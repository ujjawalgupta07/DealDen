package com.example.productservice.dto.response;

import com.example.productservice.entity.Categories;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Float price;
    private String imageUrl;
    private Categories category;
}
