package com.example.productservice.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryResponseDTO category;
}
