package com.example.orderservice.mapper;

import com.example.orderservice.dto.response.CreateOrderItemResponseDTO;
import com.example.orderservice.dto.response.CreateOrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static CreateOrderResponseDTO toResponseDTO(Order order) {
        CreateOrderResponseDTO dto = new CreateOrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setUsername(order.getUsername());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setStatus(order.getOrderStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<CreateOrderItemResponseDTO> itemDTOs = order.getOrderItems().stream()
                .map(OrderMapper::toItemDTO)
                .collect(Collectors.toList());

        dto.setOrderItems(itemDTOs);
        return dto;
    }

    private static CreateOrderItemResponseDTO toItemDTO(OrderItem item) {
        CreateOrderItemResponseDTO dto = new CreateOrderItemResponseDTO();
        dto.setProductId(item.getProductId());
        dto.setPriceAtPurchase(item.getPriceAtPurchase());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
