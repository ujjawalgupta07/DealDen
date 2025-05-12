package com.example.orderservice.service.impl;

import com.example.commons.dto.request.OrderPlacedEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.kafka.producer.KafkaPublisher;
import com.example.orderservice.mapper.OrderPlacedEventMapper;
import com.example.orderservice.service.interfaces.EventPublisherService;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherServiceImpl implements EventPublisherService {

    private final KafkaPublisher kafkaPublisher;

    public EventPublisherServiceImpl(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    @Override
    public void publishOrderPlacedEvent(Order order) {
        OrderPlacedEvent event = OrderPlacedEventMapper.toEvent(order);
        kafkaPublisher.publishOrderPlaced(event);
    }
}
