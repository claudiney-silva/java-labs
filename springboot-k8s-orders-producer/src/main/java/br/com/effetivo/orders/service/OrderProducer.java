package br.com.effetivo.orders.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.effetivo.orders.domain.Order;

@Service
public class OrderProducer {
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    private final String topic;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, Order> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Order order){
        kafkaTemplate.send(topic, order).addCallback(
                success -> logger.info("Message send" + success.getProducerRecord().value()),
                failure -> logger.info("Message failure" + failure.getMessage())
        );
    }   
}
