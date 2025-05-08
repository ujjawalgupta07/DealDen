package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.kafka.dto.OrderPlacedEvent;
import com.example.orderservice.mapper.OrderPlacedEventMapper;
import com.example.orderservice.service.interfaces.EventPublisherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherServiceImpl implements EventPublisherService {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public EventPublisherServiceImpl(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${kafka.topic.order-placed}")
    private String orderPlacedTopic;

    @Override
    public void publishOrderPlacedEvent(Order order) {
        OrderPlacedEvent event = OrderPlacedEventMapper.toEvent(order);
        kafkaTemplate.send(orderPlacedTopic, event);
    }
}
