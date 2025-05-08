package com.example.orderservice.dto.response;

import com.example.orderservice.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CreateOrderResponseDTO {

    private Long orderId;
    private Double totalPrice;
    private OrderStatus status;
    private List<CreateOrderItemResponseDTO> orderItems;
    private String username;
    private String deliveryAddress;
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
