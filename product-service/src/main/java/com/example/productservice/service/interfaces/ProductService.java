package com.example.productservice.service.interfaces;

import com.example.productservice.dto.request.ValidateProductRequestDTO;
import com.example.productservice.dto.response.ValidateProductResponseDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product getProductById(Long productId) throws ProductNotFoundException;

    Product createProduct(String title,
                          String description,
                          String categoryTitle,
                          BigDecimal price,
                          String image) throws ProductAlreadyExistsException, CategoryAlreadyExistsException;


    List<Product> getAllProducts();

    Product deleteProductById(Long productId);

    List<ValidateProductResponseDTO> validateProducts(ValidateProductRequestDTO request) throws InvalidProductIdException;

    void deleteProduct(Long productId) throws ProductNotFoundException;

    Product updateProduct(Long productId, String title, String description, BigDecimal price, String imageUrl, Long categoryId) throws ProductNotFoundException, CategoryNotFoundException;
}
