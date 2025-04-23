package com.example.productservice.service.interfaces;

import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product getProductById(Long id) throws ProductNotFoundException;

    Product createProduct(String title,
                          String description,
                          String categoryTitle,
                          BigDecimal price,
                          String image) throws ProductAlreadyExistsException, CategoryAlreadyExistsException;


    List<Product> getAllProducts();

    Product deleteProductById(Long productId);
}
