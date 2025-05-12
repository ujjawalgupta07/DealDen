package com.example.orderservice.service.impl;

import com.example.commons.config.UserContext;
import com.example.commons.exception.InvalidProductIdException;
import com.example.orderservice.dto.request.CreateOrderItemRequestDTO;
import com.example.orderservice.dto.request.CreateOrderRequestDTO;
import com.example.orderservice.dto.response.ValidateProductResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.interfaces.EventPublisherService;
import com.example.orderservice.service.interfaces.PlaceOrderUseCase;
import com.example.orderservice.service.interfaces.ProductValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceOrderUseCaseImpl implements PlaceOrderUseCase {

    private final ProductValidatorService productValidatorService;
    private final OrderRepository orderRepository;
    private final EventPublisherService eventPublisherService;
    private final UserContext userContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceOrderUseCaseImpl.class.getName());


    public PlaceOrderUseCaseImpl(ProductValidatorService productValidatorService, OrderRepository orderRepository, EventPublisherService eventPublisherService, UserContext userContext) {
        this.productValidatorService = productValidatorService;
        this.orderRepository = orderRepository;
        this.eventPublisherService = eventPublisherService;
        this.userContext = userContext;
    }

    @Override
    public Order placeOrder(CreateOrderRequestDTO requestDTO) throws InvalidProductIdException {
        String username = userContext.getUsername();

        LOGGER.info("Placing order for :: {}", username);

        // Step 1: Extract product IDs
        Set<Long> productIds = requestDTO.getOrderItems().stream()
                .map(CreateOrderItemRequestDTO::getProductId)
                .collect(Collectors.toSet());

        // Step 2: Validate products via ProductValidatorService
        Map<Long, ValidateProductResponseDTO> productMap = productValidatorService.validateProducts(productIds);

        // Step 3: Check for any invalid product
        if (productMap.size() != productIds.size()) {
            throw new InvalidProductIdException("One or more products are invalid");
        }

        // Step 4: Build Order and OrderItems
        Order order = new Order();
        order.setUsername(username);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderItems(requestDTO.getOrderItems().stream().map(itemDTO -> {
            ValidateProductResponseDTO product = productMap.get(itemDTO.getProductId());
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(product.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceAtPurchase(product.getPrice());
            return item;
        }).collect(Collectors.toList()));

        // Step 5: Persist order
        Order savedOrder = orderRepository.save(order);

        LOGGER.info("Order successfully placed");

        // Step 6: Publish event
        eventPublisherService.publishOrderPlacedEvent(savedOrder);

        return savedOrder;
    }

}