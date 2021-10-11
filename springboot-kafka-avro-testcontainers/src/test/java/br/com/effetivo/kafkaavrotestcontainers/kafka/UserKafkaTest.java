package br.com.effetivo.kafkaavrotestcontainers.kafka;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventCreate;
import br.com.effetivo.kafkaavrotestcontainers.service.KafkaService;
import br.com.effetivo.kafkaavrotestcontainers.testcontainer.KafkaTestcontainer;
import kafka.server.ClientQuotaManager;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class UserKafkaTest extends KafkaTestcontainer {

    @Autowired
    private KafkaService kafkaService;

    private static final String EVENT_TYPE_KEY = "eventType";
    private static final String EVENT_UPDATE = "UPDATE";
    private static final String EVENT_DELETE = "DELETE";
    private static final String TOPIC = "users";

    private ClientQuotaManager.UserEntity existingUser;
    private final String existingUserId = UUID.randomUUID().toString();

    KafkaConsumer consumer;
    KafkaProducer<Object, Object> producer;

    @BeforeEach
    void prepare() {
        consumer = createConsumer();
        consumer.subscribe(Collections.singleton(TOPIC));
        Duration duration = Duration.ofSeconds(5);
        consumer.poll(duration);
        producer = createProducer();
    }

    @AfterEach
    void cleanup() {
        producer.close();
        consumer.close();
    }

    @Test
    void testSendMessage() throws ExecutionException, InterruptedException {

        UserEventCreate event = UserEventCreate
                .newBuilder()
                .setId(UUID.randomUUID().toString())
                .setFirstName("Jane")
                .setLastName("Doe")
                .build();

        List<Header> headers = singletonList(new RecordHeader(EVENT_TYPE_KEY, EVENT_UPDATE.getBytes()));
        ProducerRecord record = new ProducerRecord<>(TOPIC, null, event.getId(), event, headers);

        sendEvent(producer, record);

        ConsumerRecord<String, UserEventCreate> singleRecord =
                KafkaTestUtils.getSingleRecord(consumer, TOPIC);

        assertThat(singleRecord.value()).isEqualTo(event);
    }

    @Test
    void testSendMessageByService() throws ExecutionException, InterruptedException {

        UserEventCreate event = UserEventCreate
                .newBuilder()
                .setId(UUID.randomUUID().toString())
                .setFirstName("Jane")
                .setLastName("Doe")
                .build();

        kafkaService.send(event.getId(), event);

        ConsumerRecord<String, GenericRecord> singleRecord =
                KafkaTestUtils.getSingleRecord(consumer, TOPIC);

        assertThat(singleRecord.value()).isEqualTo(event);
    }

}
