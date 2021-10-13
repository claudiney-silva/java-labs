package br.com.effetivo.serviceone.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SampleController {

    private final DiscoveryClient discoveryClient;

    @RequestMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello World");
    }

    @GetMapping("/services")
    public Optional<URI> serviceURL() {
        return discoveryClient.getInstances("ServiceOne")
                .stream()
                .map(instance -> instance.getUri())
                .findFirst();
    }


    @GetMapping("/discovery")
    public Mono<String> discovery() throws ServiceUnavailableException {
        URI uri = serviceURL().get();

        WebClient webClient = WebClient.builder()
            .baseUrl(uri.toString())
            //.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();

        return webClient.get()
            .uri("/hello")
            .retrieve()
            .bodyToMono(String.class)
            .map(s -> s + " by service discovery");
    }

}
