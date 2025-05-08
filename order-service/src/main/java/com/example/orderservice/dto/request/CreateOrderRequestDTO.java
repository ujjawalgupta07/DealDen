package com.example.orderservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequestDTO {

    @Valid
    @NotEmpty(message = "There should be at least 1 item in the order")
    private List<CreateOrderItemRequestDTO> orderItems;

    @NotBlank(message = "Delivery address should be provided")
    private String deliveryAddress;


    public CreateOrderRequestDTO() {
    }

    public @NotEmpty(message = "There should be at least 1 item in the order") List<CreateOrderItemRequestDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(@NotEmpty(message = "There should be at least 1 item in the order") List<CreateOrderItemRequestDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public @NotBlank String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(@NotBlank String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
