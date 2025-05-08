package com.example.orderservice.service.impl;

import com.example.orderservice.config.ProductServiceClient;
import com.example.orderservice.dto.request.CreateOrderItemRequestDTO;
import com.example.orderservice.dto.response.ValidateProductResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.exception.InvalidProductIdException;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductServiceClient productServiceClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order placeOrder(String username, List<CreateOrderItemRequestDTO> items, String deliveryAddress) throws InvalidProductIdException {
        List<Long> productIds = items.stream()
                .map(CreateOrderItemRequestDTO::getProductId)
                .distinct()
                .collect(Collectors.toList());

        // Call product service to validate product IDs
        List<ValidateProductResponseDTO> validProducts = productServiceClient.validateProducts(productIds);

        Map<Long, ValidateProductResponseDTO> productMap = validProducts.stream()
                .collect(Collectors.toMap(ValidateProductResponseDTO::getProductId, p -> p));

        // Check for invalid product IDs
        List<Long> invalidIds = productIds.stream()
                .filter(id -> !productMap.containsKey(id))
                .toList();

        if (!invalidIds.isEmpty()) {
            throw new InvalidProductIdException("Invalid Product IDs: " + invalidIds);
        }

        // Create order
        Order order = new Order();
        order.setUsername(username);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        // Create order items
        Order finalOrder = order;
        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    ValidateProductResponseDTO product = productMap.get(item.getProductId());
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(finalOrder);
                    orderItem.setProductId(product.getProductId());
                    orderItem.setPriceAtPurchase(product.getPrice());
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);

        return order;
    }
}
