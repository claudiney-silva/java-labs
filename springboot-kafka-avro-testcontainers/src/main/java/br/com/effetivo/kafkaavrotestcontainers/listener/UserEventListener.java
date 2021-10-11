package br.com.effetivo.kafkaavrotestcontainers.listener;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventCreate;
import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@KafkaListener(
        topics = "${myapp.topic.name}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
)
public class UserEventListener {

    @KafkaHandler
    public void consumerCreate(@Headers Map<String, String> headers,
                               @Payload UserEventCreate payload,
                               Acknowledgment ack) {

        log.info("EventCreate: {}", payload.getFirstName());
    }

    @KafkaHandler
    public void consumerUpdate(@Headers Map<String, String> headers,
                               @Payload UserEventUpdate payload,
                               Acknowledgment ack) {

        log.info("EventUpdate: {}", payload.getFirstName());
    }

    @KafkaHandler(isDefault = true)
    public void consumer(final GenericRecord genericRecord, final Acknowledgment ack) {
        log.info("Invalid Event, payload: {}", genericRecord);
        ack.acknowledge();
    }

}
