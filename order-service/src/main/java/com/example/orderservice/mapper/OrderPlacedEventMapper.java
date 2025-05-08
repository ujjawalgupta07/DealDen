package com.example.orderservice.mapper;

import com.example.orderservice.entity.Order;
import com.example.orderservice.kafka.dto.OrderPlacedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class OrderPlacedEventMapper {

    public static OrderPlacedEvent toEvent(Order order) {
        List<OrderPlacedEvent.OrderItem> items = order.getOrderItems().stream().map(item -> {
            OrderPlacedEvent.OrderItem productItem = new OrderPlacedEvent.OrderItem();
            productItem.setProductId(item.getProductId());
            productItem.setQuantity(item.getQuantity());
            return productItem;
        }).collect(Collectors.toList());

         OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
         orderPlacedEvent.setOrderId(String.valueOf(order.getId()));
         orderPlacedEvent.setUsername(order.getUsername());
         orderPlacedEvent.setStatus(String.valueOf(order.getOrderStatus()));
         orderPlacedEvent.setProducts(items);

         return orderPlacedEvent;
    }
}
