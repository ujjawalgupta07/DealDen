package com.example.productservice.controller;

import com.example.commons.aop.annotations.HasAnyRole;
import com.example.productservice.builder.ProductMapper;
import com.example.productservice.dto.request.CreateProductRequestDTO;
import com.example.productservice.dto.request.UpdateProductRequestDTO;
import com.example.productservice.dto.request.ValidateProductRequestDTO;
import com.example.productservice.dto.response.ProductResponseDTO;
import com.example.productservice.dto.response.ValidateProductResponseDTO;
import com.example.productservice.entity.Product;
import com.example.commons.exception.*;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class.getName());

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
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

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
        if (null == productId) {
            throw new InvalidProductIdException("Invalid Product Id.");
        }

        Product product = productService.getProductById(productId);

        return ResponseEntity.ok(ProductMapper.convertToProductResponseDTO(product));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProductById(@PathVariable("id") Long productId)
            throws InvalidProductIdException, ProductNotFoundException {

        if (null == productId) {
            throw new InvalidProductIdException("Invalid Product Id.");
        }
        Product product = productService.deleteProductById(productId);

        if (Objects.isNull(product)) {
            throw new ProductNotFoundException("Product not found.");
        }
        return ResponseEntity.ok(ProductMapper.convertToProductResponseDTO(product));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId,
                                                            @RequestBody @Valid UpdateProductRequestDTO request)
            throws CategoryNotFoundException, ProductNotFoundException {
        Product product = productService.updateProduct(
                productId,
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getImageUrl(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(ProductMapper.convertToProductResponseDTO(product));
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @HasAnyRole({"ROLE_VENDOR", "ROLE_ADMIN"})
    @PostMapping("/validate-products")
    public ResponseEntity<List<ValidateProductResponseDTO>> validateProducts(@RequestBody @Valid ValidateProductRequestDTO request)
            throws InvalidProductIdException {
        List<ValidateProductResponseDTO> response = productService.validateProducts(request);
        return ResponseEntity.ok(response);
    }

}
