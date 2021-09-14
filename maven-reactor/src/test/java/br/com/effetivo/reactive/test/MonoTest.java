package br.com.effetivo.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.Flow;

@Slf4j
/**
 * Reactive Streams
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher <- (subscribe) Subscriber
 * Subscription is created
 * Publisher (onSubscribe with the subscription) -> Subscriber
 * Subscription <- (request N) Subscriber
 * Publisher -> (onNext) Subscriber
 * until:
 * 1. Publisher sends all the objects requested. (backpressure)
 * 2. Publisher sends all the objects it has. (onComplete) subscriber and subscription will be canceled.
 * 3. There is an error. (onError) -> subscriber and subscription will be canceled.
 */
public class MonoTest {

    @Test
    public void monoSubscriber() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe();

        log.info("---------");

        StepVerifier.create(mono)
            .expectNext(name)
            .verifyComplete();

    }

    @Test
    public void monoSubscriberConsumer() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe(s->log.info("Value {}", s));

        log.info("---------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerError() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .map(s -> {
                    throw new RuntimeException("Test mono with error");
                });


        mono.subscribe(s->log.info("Value {}", s), s -> log.error("Something wrong: {}", s.getMessage()));
        mono.subscribe(s->log.info("Value {}", s), Throwable::printStackTrace);

        log.info("---------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoSubscriberConsumerComplete() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(
                s->log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("---------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerSubscription() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(
                s->log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"),
                Subscription::cancel);

        log.info("---------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerSubscriptionBackpressure() {
        String name = "Teste";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(
                s->log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"),
                subscription -> subscription.request(5));

        log.info("---------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnMethods() {
        String name = "Teste";
        Mono<Object> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(longNumber -> log.info("Request received {}"))
                .doOnNext(s -> log.info("Value here {}", s))
                .flatMap(s -> Mono.empty())
                .doOnNext(s -> log.info("Value here {}", s)) // will not be executed
                .doOnSuccess(s -> log.info("Success executed"));

        mono.subscribe(
                s->log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"),
                Subscription::cancel);

        log.info("---------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnError() {
        Mono<Object> mono = Mono.error(new IllegalArgumentException("Sample error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .doOnNext(s -> log.info("Test {}", s))
                .log();

        StepVerifier.create(mono)
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    public void monoDoOnErrorResume() {
        String value = "The string value";
        Mono<Object> mono = Mono.error(new IllegalArgumentException("Sample error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .onErrorResume(s ->{
                    log.info("Inside error resume");
                    return Mono.just(value);
                })
                .log();

        StepVerifier.create(mono)
                .expectNext(value)
                .verifyComplete();
    }

    @Test
    public void monoDoOnErrorReturn() {
        String value = "The string value";
        Mono<Object> mono = Mono.error(new IllegalArgumentException("Sample error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .onErrorReturn("EMPTY")
                .onErrorResume(s ->{
                    log.info("Inside error resume");
                    return Mono.just(value);
                })
                .log();

        StepVerifier.create(mono)
                .expectNext("EMPTY")
                .verifyComplete();
    }
}
