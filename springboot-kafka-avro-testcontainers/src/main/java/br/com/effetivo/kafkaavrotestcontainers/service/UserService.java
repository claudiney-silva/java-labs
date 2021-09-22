package br.com.effetivo.kafkaavrotestcontainers.service;

import br.com.effetivo.kafkaavrotestcontainers.avro.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface UserService {
    public ListenableFuture<SendResult<String, UserEvent>> send(UserEvent userEvent);
    public SendResult<String, UserEvent> sendSynchronous(UserEvent userEvent) throws ExecutionException, InterruptedException, TimeoutException;
    public void handleRecovery(ConsumerRecord<String, UserEvent> consumerRecord);
}
