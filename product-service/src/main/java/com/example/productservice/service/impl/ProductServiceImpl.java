package com.example.productservice.service.impl;


import com.example.productservice.dto.request.ValidateProductRequestDTO;
import com.example.productservice.dto.response.ValidateProductResponseDTO;
import com.example.productservice.entity.Category;
import com.example.productservice.entity.Product;
import com.example.productservice.enums.DeletedStatus;
import com.example.commons.exception.*;
import com.example.productservice.repository.CategoryRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

    @Override
    public List<ValidateProductResponseDTO> validateProducts(ValidateProductRequestDTO request) throws InvalidProductIdException {
        // Fetch all products matching the given IDs
        List<Product> products = productRepository.findAllById(request.getProductIds());

        // Find which IDs were missing
        Set<Long> foundIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        List<Long> invalidIds = request.getProductIds().stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!invalidIds.isEmpty()) {
            throw new InvalidProductIdException("Invalid product IDs: " + invalidIds);
        }

        List<ValidateProductResponseDTO> responseDTOList = new ArrayList<>();
        // Build response
        for(Product product : products){
            ValidateProductResponseDTO responseDTO = new ValidateProductResponseDTO();
            responseDTO.setProductId(product.getId());
            responseDTO.setTitle(product.getTitle());
            responseDTO.setPrice(product.getPrice());

            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId,
                                 String title,
                                 String description,
                                 BigDecimal price,
                                 String imageUrl,
                                 Long categoryId)
            throws ProductNotFoundException, CategoryNotFoundException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
            product.setCategory(category);
        }

        if (title != null) product.setTitle(title);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);
        if (imageUrl != null) product.setImageUrl(imageUrl);

        return productRepository.save(product);
    }

}
