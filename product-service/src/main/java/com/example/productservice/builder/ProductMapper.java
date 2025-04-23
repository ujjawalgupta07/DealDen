package com.example.productservice.builder;

import com.example.productservice.dto.response.CategoryResponseDTO;
import com.example.productservice.dto.response.ProductResponseDTO;
import com.example.productservice.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    /**
     * Converts the Incoming Object to ProductResponseDTO
     *
     * @param product : Products Entity Object
     * @return ProductResponseDTO
     */
    public static ProductResponseDTO convertToProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .category(CategoryResponseDTO.builder()
                        .id(product.getCategory().getId())
                .title(product.getCategory().getTitle())
                .build())
                .build();
    }

    public static List<ProductResponseDTO> convertToProductResponseDTOList(List<Product> products) {
        return
                products
                        .stream()
                        .map(ProductMapper::convertToProductResponseDTO)
                        .collect(Collectors.toList());
    }

}
