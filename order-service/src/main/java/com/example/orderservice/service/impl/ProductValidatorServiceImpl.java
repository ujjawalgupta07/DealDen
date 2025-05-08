package com.example.orderservice.service.impl;

import com.example.orderservice.config.ProductServiceClient;
import com.example.orderservice.dto.request.ValidateProductRequestDTO;
import com.example.orderservice.dto.response.ValidateProductResponseDTO;
import com.example.orderservice.service.interfaces.ProductValidatorService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductValidatorServiceImpl implements ProductValidatorService {

    private final ProductServiceClient productServiceClient;

    public ProductValidatorServiceImpl(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @Override
    public Map<Long, ValidateProductResponseDTO> validateProducts(Set<Long> productIds) {
        ValidateProductRequestDTO validateProductRequestDTO = new ValidateProductRequestDTO();
        validateProductRequestDTO.setProductIds(productIds.stream().toList());
        List<ValidateProductResponseDTO> products = productServiceClient.validateProducts(validateProductRequestDTO);
        return products.stream().collect(Collectors.toMap(ValidateProductResponseDTO::getProductId, Function.identity()));
    }
}