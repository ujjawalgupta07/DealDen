package com.example.orderservice.service.interfaces;

import com.example.orderservice.dto.response.ValidateProductResponseDTO;

import java.util.Map;
import java.util.Set;

public interface ProductValidatorService {
    Map<Long, ValidateProductResponseDTO> validateProducts(Set<Long> productIds);
}
