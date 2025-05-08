package com.example.orderservice.config;

import com.example.orderservice.dto.request.ValidateProductRequestDTO;
import com.example.orderservice.dto.response.ValidateProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-service", path = "/api/products")
public interface ProductServiceClient {

    @PostMapping("/validate")
    List<ValidateProductResponseDTO> validateProducts(List<Long> productIds);
}
