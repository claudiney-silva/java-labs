package br.com.effetivo.kafkaavrotestcontainers.service;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEventCreate;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface KafkaService {
    public ListenableFuture<SendResult<String, GenericRecord>> send(String transactionId, GenericRecord record);
    public SendResult<String, GenericRecord> sendSynchronous(String transactionId, GenericRecord record) throws ExecutionException, InterruptedException, TimeoutException;
    public void handleRecovery(ConsumerRecord<String, GenericRecord> consumerRecord);
}
