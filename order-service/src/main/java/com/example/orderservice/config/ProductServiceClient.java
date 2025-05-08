package com.example.orderservice.config;

import com.example.orderservice.dto.request.ValidateProductRequestDTO;
import com.example.orderservice.dto.response.ValidateProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-service", path = "/api/v1/product")
public interface ProductServiceClient {

    @PostMapping("/validate-products")
    List<ValidateProductResponseDTO> validateProducts(ValidateProductRequestDTO validateProductRequestDTO);
}
