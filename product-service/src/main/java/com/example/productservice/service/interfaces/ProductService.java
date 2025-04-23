package com.example.productservice.service.interfaces;

import com.example.productservice.entity.Products;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.ProductAlreadyExistsException;

import java.util.List;

public interface ProductService {

    Products getProductById(Long id);

    Products createProduct(String title,
                          String description,
                          String category,
                          String price,
                          String image) throws ProductAlreadyExistsException, CategoryAlreadyExistsException;


    List<Products> getAllProducts();

    Products deleteProductById(Long productId);
}
