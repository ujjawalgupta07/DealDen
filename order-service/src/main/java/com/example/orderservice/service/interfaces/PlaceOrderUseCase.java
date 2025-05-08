package com.example.orderservice.service.interfaces;

import com.example.commons.exception.InvalidProductIdException;
import com.example.orderservice.dto.request.CreateOrderRequestDTO;
import com.example.orderservice.entity.Order;

public interface PlaceOrderUseCase {

    Order placeOrder(CreateOrderRequestDTO request) throws InvalidProductIdException;
}
