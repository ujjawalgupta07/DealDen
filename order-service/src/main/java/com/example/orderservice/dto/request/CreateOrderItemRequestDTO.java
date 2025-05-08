package com.example.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrderItemRequestDTO {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "Product quantity is required")
    @Min(value = 1, message = "Minimum quantity should be 1")
    private Integer quantity;

    public CreateOrderItemRequestDTO() {
    }

    public @NotNull(message = "Product Id is required") Long getProductId() {
        return productId;
    }

    public void setProductId(@NotNull(message = "Product Id is required") Long productId) {
        this.productId = productId;
    }

    public @NotNull(message = "Product quantity is required") @Min(value = 1, message = "Minimum quantity should be 1") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = "Product quantity is required") @Min(value = 1, message = "Minimum quantity should be 1") Integer quantity) {
        this.quantity = quantity;
    }
}
