package com.example.orderservice.service.interfaces;

import com.example.orderservice.entity.Order;

public interface EventPublisherService {

    void publishOrderPlacedEvent(Order order);
}
