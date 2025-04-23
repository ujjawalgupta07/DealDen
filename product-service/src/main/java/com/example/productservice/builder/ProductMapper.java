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

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(product.getCategory().getId());
        categoryResponseDTO.setTitle(product.getCategory().getTitle());

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setImageUrl(product.getImageUrl());
        productResponseDTO.setCategory(categoryResponseDTO);

        return productResponseDTO;
    }

    public static List<ProductResponseDTO> convertToProductResponseDTOList(List<Product> products) {
        return
                products
                        .stream()
                        .map(ProductMapper::convertToProductResponseDTO)
                        .collect(Collectors.toList());
    }

}
