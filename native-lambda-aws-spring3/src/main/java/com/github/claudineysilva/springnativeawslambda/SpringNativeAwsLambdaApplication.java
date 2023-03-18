package com.github.claudineysilva.springnativeawslambda;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class SpringNativeAwsLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNativeAwsLambdaApplication.class, args);
	}

	@Bean
	public Function<JsonNode, String> reverseString() {

		return value -> {
			System.out.printf(value.toPrettyString());
			return "sucesso";
		};
	}
}
