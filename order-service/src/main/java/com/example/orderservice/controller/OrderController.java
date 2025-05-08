package com.example.orderservice.controller;

import com.example.commons.config.UserContext;
import com.example.orderservice.dto.request.CreateOrderRequestDTO;
import com.example.orderservice.dto.response.CreateOrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.interfaces.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @Autowired
    private final UserContext userContext;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> placeOrder(@RequestBody @Valid CreateOrderRequestDTO request) {
        String username = userContext.getUsername();
        Order order = orderService.placeOrder(username, request.getOrderItems(), request.getDeliveryAddress());
        CreateOrderResponseDTO responseDTO = objectMapper.convertValue(order, CreateOrderResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }
}
