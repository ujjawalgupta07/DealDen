package com.example.orderservice.controller;

import com.example.commons.aop.annotations.IsUser;
import com.example.commons.config.UserContext;
import com.example.commons.exception.InvalidProductIdException;
import com.example.orderservice.dto.request.CreateOrderRequestDTO;
import com.example.orderservice.dto.response.CreateOrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.service.interfaces.PlaceOrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class.getName());

    public OrderController(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    @IsUser
    @PostMapping()
    public ResponseEntity<CreateOrderResponseDTO> placeOrder(@RequestBody @Valid CreateOrderRequestDTO request) throws InvalidProductIdException {
        Order order = placeOrderUseCase.placeOrder(request);
        CreateOrderResponseDTO responseDTO = OrderMapper.toResponseDTO(order);
        return ResponseEntity.ok(responseDTO);
    }
}
