package br.com.effetivo.servicetwo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SampleController {

    private final DiscoveryClient discoveryClient;
    private final WebClient webClient;
    private final ReactiveLoadBalancer.Factory<ServiceInstance> serviceInstanceFactory;

    @Value("${server.port}")
    private String serverPort;

    private Mono<ServiceInstance> findServerInstance() {
        return Mono.from(serviceInstanceFactory.getInstance("service-two").choose())
                .map(response -> response.getServer());
    }

    @RequestMapping("/hello")
    public Mono<String> hello() {
        log.info("Hello World para: "+serverPort);
        return Mono.just("Hello World "+ serverPort);
    }

    @GetMapping("/services")
    public Flux<List<ServiceInstance>> serviceURL() {
        return Flux.just(discoveryClient.getInstances("service-two"));
    }

    @GetMapping("/balancer")
    public Mono<String> discovery() throws ServiceUnavailableException {

        Mono<String> result = findServerInstance()
                .map(server -> "http://" + server.getHost() + ':' + server.getPort() + "/hello")
                .flatMap(url -> webClient.get().uri(url).retrieve().bodyToMono(String.class))
                .map(s -> "Originou do "+serverPort+" e invocou " + s);

        return result;

    }

}
