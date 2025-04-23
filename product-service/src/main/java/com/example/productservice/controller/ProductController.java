package com.example.productservice.controller;

import com.example.productservice.builder.ProductMapper;
import com.example.productservice.dto.request.CreateProductRequestDTO;
import com.example.productservice.dto.response.ProductResponseDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.InvalidProductIdException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.service.interfaces.ProductService;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller is responsible for 3 things ::
 * 1. validations on the request body / request params
 * 2. calling the service layer and get the details.
 * 3. Model to DTO Conversion. (Model is returned by Service).
 */
@RestController
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductController(@Qualifier("selfProductService") ProductService svc, ProductMapper mapper) {
        this.productService = svc;
        this.productMapper = mapper;
    }

    @PostMapping("/product")
    public ProductResponseDTO createProduct(@RequestBody CreateProductRequestDTO createProductRequestDTO)
            throws BadRequestException, ProductAlreadyExistsException, CategoryAlreadyExistsException {

        validateCreateProductRequest(createProductRequestDTO);
        Product product = productService.createProduct(createProductRequestDTO.getTitle(),
                createProductRequestDTO.getDescription(),
                createProductRequestDTO.getCategory(),
                createProductRequestDTO.getPrice(),
                createProductRequestDTO.getImage());

        return productMapper.convertToProductResponseDTO(product);
    }

    private void validateCreateProductRequest(CreateProductRequestDTO createProductRequestDTO)
            throws BadRequestException {
        if(StringUtils.isBlank(createProductRequestDTO.getTitle())) {
            throw new BadRequestException("Product Title is missing");
        }
        if(StringUtils.isBlank(createProductRequestDTO.getDescription())) {
            throw new BadRequestException("Product Description is missing");
        }
        if(StringUtils.isBlank(createProductRequestDTO.getPrice())) {
            throw new BadRequestException("Product Price is missing");
        }
    }

    @GetMapping("/products")
    public List<ProductResponseDTO> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        if (CollectionUtils.isEmpty(productList)) {
            return null;
        }

        List<ProductResponseDTO> response = new ArrayList<>();
        for (Product product : productList) {
            response.add(productMapper.convertToProductResponseDTO(product));
        }

        return response;
    }

    @GetMapping("/product/{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") String productId)
            throws ProductNotFoundException, InvalidProductIdException {

        if(null == productId){
           throw new InvalidProductIdException("Invalid Product Id.");
        }
        Product product = productService.getProductById(Long.valueOf(productId));

        if(Objects.isNull(product)){
            throw new ProductNotFoundException("Product not found.");
        }
        return productMapper.convertToProductResponseDTO(product);
    }

    @DeleteMapping("/product/{id}")
    public ProductResponseDTO deleteProductById(@PathVariable("id") Long productId)
            throws InvalidProductIdException, ProductNotFoundException {

        if(null == productId){
            throw new InvalidProductIdException("Invalid Product Id.");
        }
        Product product = productService.deleteProductById(productId);

        if(Objects.isNull(product)){
            throw new ProductNotFoundException("Product not found.");
        }
        return productMapper.convertToProductResponseDTO(product);
    }
}
