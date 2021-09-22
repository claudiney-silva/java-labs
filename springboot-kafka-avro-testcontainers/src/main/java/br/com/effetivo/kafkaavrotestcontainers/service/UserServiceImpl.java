package br.com.effetivo.kafkaavrotestcontainers.service;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${myapp.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Override
    public ListenableFuture<SendResult<String, UserEvent>> send(UserEvent message) {
        ProducerRecord<String, UserEvent> producerRecord = buildProducerRecord(message);
        ListenableFuture<SendResult<String, UserEvent>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, UserEvent>>() {
                    @Override
                    public void onSuccess(SendResult<String, UserEvent> result) {
                        log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
                    }
                }
        );
        return listenableFuture;
    }

    @Override
    public SendResult<String, UserEvent> sendSynchronous(UserEvent message) throws ExecutionException, InterruptedException, TimeoutException {
        try {
            return kafkaTemplate.sendDefault(message.getId(), message).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void handleRecovery(ConsumerRecord<String, UserEvent> consumerRecord) {
        log.info("handleRecovery for {}", consumerRecord.value());
    }

    private ProducerRecord<String, UserEvent> buildProducerRecord(UserEvent userEvent) {
        List<Header> recordHeaders = Arrays.asList(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topicName, null, userEvent.getId(), userEvent, recordHeaders);
    }

}
