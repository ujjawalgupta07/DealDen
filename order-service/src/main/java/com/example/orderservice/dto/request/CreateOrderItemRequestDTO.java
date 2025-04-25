package com.example.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateOrderItemRequestDTO {

    @NotBlank(message = "Product Id is required")
    private Long productId;

    @NotBlank(message = "Product quantity is required")
    @Min(value = 1, message = "Minimum quantity should be 1")
    private Integer quantity;

    public CreateOrderItemRequestDTO() {
    }

    public @NotBlank(message = "Product Id is required") Long getProductId() {
        return productId;
    }

    public void setProductId(@NotBlank(message = "Product Id is required") Long productId) {
        this.productId = productId;
    }

    public @NotBlank(message = "Product quantity is required") @Min(value = 1, message = "Minimum quantity should be 1") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotBlank(message = "Product quantity is required") @Min(value = 1, message = "Minimum quantity should be 1") Integer quantity) {
        this.quantity = quantity;
    }
}
