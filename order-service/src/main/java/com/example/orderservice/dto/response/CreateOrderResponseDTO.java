package com.example.orderservice.dto.response;

import com.example.orderservice.enums.OrderStatus;

import java.util.List;

public class CreateOrderResponseDTO {

    private Long orderId;
    private Double totalPrice;
    private OrderStatus status;
    private List<CreateOrderItemResponseDTO> orderItems;

    public CreateOrderResponseDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<CreateOrderItemResponseDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CreateOrderItemResponseDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
