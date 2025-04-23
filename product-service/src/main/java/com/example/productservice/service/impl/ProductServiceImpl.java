package com.example.productservice.service.impl;


import com.example.productservice.entity.Categories;
import com.example.productservice.entity.Products;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.repository.ProductsRepo;
import com.example.productservice.service.interfaces.CategoriesService;
import com.example.productservice.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("selfProductService")
public class ProductServiceImpl implements ProductService {

    ProductsRepo productsRepo;
    CategoriesService categoriesService;

    ProductServiceImpl(ProductsRepo productsRepo, CategoriesService categoriesService){
        this.productsRepo = productsRepo;
        this.categoriesService = categoriesService;
    }

    @Override
    public Products getProductById(Long id) {
       Products product = productsRepo.findProductsById(id);
       if(Objects.nonNull(product)){
           return product;
       }
        return null;
    }

    @Override
    public Products createProduct(String productTitle, String description, String categoryTitle, String price, String image) throws ProductAlreadyExistsException, CategoryAlreadyExistsException {

        Products existingProduct = productsRepo.findProductsByTitle(productTitle);
        if(Objects.nonNull(existingProduct)){
            throw new ProductAlreadyExistsException("Product with same title already exists");
        }

        Categories existingCategory = categoriesService.getCategoryByTitle(categoryTitle);
        if(Objects.isNull(existingCategory)){
            existingCategory = categoriesService.createCategories(categoryTitle);
        }

        Products product =
                Products.builder()
                                .title(productTitle)
                                .description(description)
                                .imageUrl(image)
                                .price(Float.valueOf(price))
                                .category(existingCategory)
                        .build();

        product.setIsDeleted("N");
        product.setCreatedAt(LocalDate.now());

        return productsRepo.save(product);
    }

    @Override
    public List<Products> getAllProducts() {
        List<Products> productsList = productsRepo.findProductsByIsDeleted("N");
        if(CollectionUtils.isEmpty(productsList)){
            return new ArrayList<>();
        }
        return productsList;
    }

    @Override
    public Products deleteProductById(Long productId) {
        Products product = productsRepo.findProductsById(productId);
        if(Objects.nonNull(product)){
            return productsRepo.updateIsDeletedById(productId);
        }
        return null;
    }
}
