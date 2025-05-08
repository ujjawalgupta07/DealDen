package com.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class CreateOrderRequestDTO {

    @NotBlank(message = "There should be at least 1 item in the order")
    private List<CreateOrderItemRequestDTO> orderItems;

    @NotBlank
    private String deliveryAddress;


    public CreateOrderRequestDTO() {
    }

    public @NotBlank(message = "There should be at least 1 item in the order") List<CreateOrderItemRequestDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(@NotBlank(message = "There should be at least 1 item in the order") List<CreateOrderItemRequestDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public @NotBlank String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(@NotBlank String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
