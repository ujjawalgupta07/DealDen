package com.example.productservice.service.impl;


import com.example.productservice.entity.Category;
import com.example.productservice.entity.Product;
import com.example.productservice.enums.DeletedStatus;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.interfaces.CategoryService;
import com.example.productservice.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
       Product product = productRepository.findProductsById(id);
       if(Objects.nonNull(product)){
           return product;
       }
        throw new ProductNotFoundException("Product with id : '" + id + "' not found.");
    }

    @Override
    public Product createProduct(String productTitle, String description, String categoryTitle, BigDecimal price, String image)
            throws ProductAlreadyExistsException, CategoryAlreadyExistsException {

        Category existingCategory;
        try {
            existingCategory = categoryService.getCategoryByTitle(categoryTitle);
        }
        catch (CategoryNotFoundException e) {
            existingCategory = categoryService.createCategory(categoryTitle);
        }

        Product existingProduct = productRepository.findProductsByTitle(productTitle);
        if(Objects.nonNull(existingProduct)){
            throw new ProductAlreadyExistsException("Product with same title already exists");
        }

        Product product = new Product();
        product.setTitle(productTitle);
        product.setDescription(description);
        product.setCategory(existingCategory);
        product.setPrice(price);
        product.setImageUrl(image);

        product.setIsDeleted(DeletedStatus.N.getValue());
        product.setCreatedAt(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findProductsByIsDeleted(DeletedStatus.N.getValue());
        if(CollectionUtils.isEmpty(productList)){
            return new ArrayList<>();
        }
        return productList;
    }

    @Override
    public Product deleteProductById(Long productId) {
        Product product = productRepository.findProductsById(productId);
//        if(Objects.nonNull(product)){
//            return productRepository.updateIsDeletedById(productId);
//        }
        return null;
    }
}
