package com.example.orderservice.service.interfaces;

import com.example.orderservice.dto.request.CreateOrderItemRequestDTO;
import com.example.orderservice.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(String username, List<CreateOrderItemRequestDTO> items, String deliveryAddress);
}