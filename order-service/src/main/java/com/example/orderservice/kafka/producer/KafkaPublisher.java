package com.example.orderservice.kafka.producer;

import com.example.commons.dto.request.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisher {

    @Value("${kafka.topics.order-placed}")
    private String orderPlaced;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublisher.class.getName());

    public KafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderPlaced(OrderPlacedEvent event){
        LOGGER.info("Publishing order placed event: {}", event.toString());
        try{
            kafkaTemplate.send(orderPlaced, event.getOrderId(), event);
        }
        catch(Exception e){
            LOGGER.error("Error in publishing event {}", e.getMessage());
        }
        LOGGER.info("Published order event");

    }
}
