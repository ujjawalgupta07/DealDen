package com.example.productservice.controller;

import com.example.productservice.aop.annotations.HasAnyRole;
import com.example.productservice.aop.annotations.IsAdmin;
import com.example.productservice.aop.annotations.IsUser;
import com.example.productservice.aop.annotations.IsVendor;
import com.example.productservice.builder.ProductMapper;
import com.example.productservice.dto.request.CreateProductRequestDTO;
import com.example.productservice.dto.response.ProductResponseDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.InvalidProductIdException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.service.interfaces.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Controller is responsible for 3 things ::
 * 1. validations on the request body / request params
 * 2. calling the service layer and get the details.
 * 3. Model to DTO Conversion. (Model is returned by Service).
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFactory.class);

    public ProductController(@Qualifier("productServiceImpl") ProductService svc) {
        this.productService = svc;
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO createProductRequestDTO)
            throws ProductAlreadyExistsException, CategoryAlreadyExistsException {

        LOGGER.info("Creating product with title : {} ", createProductRequestDTO.getTitle());
        Product product = productService.createProduct(createProductRequestDTO.getTitle(),
                createProductRequestDTO.getDescription(),
                createProductRequestDTO.getCategoryTitle(),
                createProductRequestDTO.getPrice(),
                createProductRequestDTO.getImage());

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.convertToProductResponseDTO(product));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){

        LOGGER.info("Fetching all products");
        List<Product> productList = productService.getAllProducts();
        if (CollectionUtils.isEmpty(productList)) {
            return null;
        }

        return ResponseEntity.ok(ProductMapper.convertToProductResponseDTOList(productList));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long productId)
            throws ProductNotFoundException, InvalidProductIdException {

        LOGGER.info("Fetching product with id : {}", productId);
        if(null == productId){
           throw new InvalidProductIdException("Invalid Product Id.");
        }

        Product product = productService.getProductById(productId);

        return ResponseEntity.ok(ProductMapper.convertToProductResponseDTO(product));
    }

    @IsVendor
    @IsAdmin
    @DeleteMapping("/{id}")
    public ProductResponseDTO deleteProductById(@PathVariable("id") Long productId)
            throws InvalidProductIdException, ProductNotFoundException {

        if(null == productId){
            throw new InvalidProductIdException("Invalid Product Id.");
        }
        Product product = productService.deleteProductById(productId);

        if(Objects.isNull(product)){
            throw new ProductNotFoundException("Product not found.");
        }
        return ProductMapper.convertToProductResponseDTO(product);
    }
}
