package com.example.productservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ValidateProductRequestDTO {

    @NotEmpty(message = "Product IDs list cannot be empty")
    private List<Long> productIds;

    public ValidateProductRequestDTO() {
    }

    public @NotEmpty(message = "Product IDs list cannot be empty") List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(@NotEmpty(message = "Product IDs list cannot be empty") List<Long> productIds) {
        this.productIds = productIds;
    }
}
