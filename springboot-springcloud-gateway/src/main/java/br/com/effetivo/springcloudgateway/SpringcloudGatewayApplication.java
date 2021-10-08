package br.com.effetivo.springcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringcloudGatewayApplication {

	private static final String HTTP_HTTPBIN_ORG = "http://httpbin.org";
	private static final String ACCEPT = "accept";
	private static final String TEXT_HTML = "text/html";

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
					.path("/get")
					.filters(f -> f.addRequestHeader("Hello", "Custom Message"))
					.uri(HTTP_HTTPBIN_ORG + ":80"))
				.route(p -> p
					.host("*.effetivo.com.br")
					.filters(f -> f.addRequestHeader(ACCEPT, "application/json"))
					.uri(HTTP_HTTPBIN_ORG + "/json"))
				.route(p -> p
					.path("/html")
					.filters(f -> f.addRequestHeader(ACCEPT, TEXT_HTML))
					.uri(HTTP_HTTPBIN_ORG + "/html"))
				/*
				.route(p -> p
					.host("*.circuitbreaker")
						.filters(f -> f.hystrix(config -> config
							.setName("mycad")
							.setFallbackUri("forward:/fallback")))
							.uri("http://effetivo.com.br"))
				*/
				.route(p -> p
					.path("/image")
					.filters(f -> f.addRequestHeader(ACCEPT, "image/webp"))
					.uri(HTTP_HTTPBIN_ORG + "/image"))
				.build();
	}
}
