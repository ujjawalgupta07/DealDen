package com.example.productservice.service.impl;


import com.example.productservice.entity.Category;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.interfaces.CategoryService;
import com.example.productservice.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("selfProductService")
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryService categoryService;

    ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(Long id) {
       Product product = productRepository.findProductsById(id);
       if(Objects.nonNull(product)){
           return product;
       }
        return null;
    }

    @Override
    public Product createProduct(String productTitle, String description, String categoryTitle, String price, String image) throws ProductAlreadyExistsException, CategoryAlreadyExistsException {

        Product existingProduct = productRepository.findProductsByTitle(productTitle);
        if(Objects.nonNull(existingProduct)){
            throw new ProductAlreadyExistsException("Product with same title already exists");
        }

        Category existingCategory = categoryService.getCategoryByTitle(categoryTitle);
        if(Objects.isNull(existingCategory)){
            existingCategory = categoryService.createCategory(categoryTitle);
        }

        Product product =
                Product.builder()
                                .title(productTitle)
                                .description(description)
                                .imageUrl(image)
                                .price(Float.valueOf(price))
                                .category(existingCategory)
                        .build();

        product.setIsDeleted("N");
        product.setCreatedAt(LocalDate.now());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findProductsByIsDeleted("N");
        if(CollectionUtils.isEmpty(productList)){
            return new ArrayList<>();
        }
        return productList;
    }

    @Override
    public Product deleteProductById(Long productId) {
        Product product = productRepository.findProductsById(productId);
        if(Objects.nonNull(product)){
            return productRepository.updateIsDeletedById(productId);
        }
        return null;
    }
}
