package br.com.effetivo.kafkaavrotestcontainers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
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
public class KafkaServiceImpl implements KafkaService {

    @Value("${myapp.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, GenericRecord> kafkaTemplate;

    @Override
    public ListenableFuture<SendResult<String, GenericRecord>> send(String transactionId, GenericRecord record) {
        ProducerRecord<String, GenericRecord> producerRecord = buildProducerRecord(transactionId, record);
        ListenableFuture<SendResult<String, GenericRecord>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, GenericRecord>>() {
                    @Override
                    public void onSuccess(SendResult<String, GenericRecord> result) {
                        log.info("Sent message=[{}] with offset=[{}]", record, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=[{}] due to : {}", record, ex.getMessage());
                    }
                }
        );
        return listenableFuture;
    }

    @Override
    public SendResult<String, GenericRecord> sendSynchronous(String transactionId, GenericRecord record) throws ExecutionException, InterruptedException, TimeoutException {
        try {
            return kafkaTemplate.sendDefault(transactionId, record).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void handleRecovery(ConsumerRecord<String, GenericRecord> consumerRecord) {
        log.info("handleRecovery for {}", consumerRecord.value());
    }

    private ProducerRecord<String, GenericRecord> buildProducerRecord(String transactionId, GenericRecord record) {
        List<Header> recordHeaders = Arrays.asList(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topicName, null, transactionId, record, recordHeaders);
    }

}
