package br.com.effetivo.kafkaavrotestcontainers.listener;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
public class UserListener {

    @KafkaListener(topics = "${myapp.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey,
                        ConsumerRecord<String, UserEvent> record,
                        Acknowledgment ack) {
        log.info("Consumed message and value {}", record.value().getFirstName());
        ack.acknowledge();
    }

}
