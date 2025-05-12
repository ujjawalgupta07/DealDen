package com.example.productservice.kafka;

import com.example.commons.dto.request.OrderPlacedEvent;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
public class OrderPlacedEventConsumer implements Serializable {

    private final ProductRepository productRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPlacedEventConsumer.class.getName());

    public OrderPlacedEventConsumer(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @KafkaListener(topics = "${kafka.topics.order-placed}", groupId = "product-service")
    @Transactional
    public void handleOrderPlaced(OrderPlacedEvent event) {
        LOGGER.info("Received OrderPlacedEvent to update inventory: {}", event);

        for (OrderPlacedEvent.OrderItem item : event.getProducts()) {
            Optional<Product> optionalProduct = productRepository.findById(item.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                int remainingStock = product.getQuantity() - item.getQuantity();

                if (remainingStock < 0) {
                    LOGGER.error("Insufficient stock for product ID: {}", item.getProductId());
                    // Optional: Publish a failed event to Kafka or notify
                    continue;
                }

                product.setQuantity(remainingStock);
                productRepository.save(product);
                LOGGER.info("Inventory updated for product ID: {}, remaining: {}", product.getId(), remainingStock);
            } else {
                LOGGER.error("Product not found for ID: {}", item.getProductId());
            }
        }
    }
}
