package com.example.productservice.builder;

import com.example.productservice.dto.response.ProductResponseDTO;
import com.example.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Converts the Incoming Object to ProductResponseDTO
     *
     * @param product : Products Entity Object
     * @return ProductResponseDTO
     */
    public ProductResponseDTO convertToProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .build();
    }

}
