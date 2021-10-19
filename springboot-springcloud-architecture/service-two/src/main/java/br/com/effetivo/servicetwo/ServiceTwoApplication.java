package br.com.effetivo.servicetwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTwoApplication.class, args);
	}

	//@LoadBalanced
	@Bean
	public WebClient webClient() {
		return WebClient
				.builder()
				//.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.build();
	}

}
