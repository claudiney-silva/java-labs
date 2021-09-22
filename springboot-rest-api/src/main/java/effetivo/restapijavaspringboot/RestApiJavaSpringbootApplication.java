package effetivo.restapijavaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiJavaSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiJavaSpringbootApplication.class, args);
	}

	/*
	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer(
			@Value("${spring.application.name}") String applicationName) {
		return (registry) -> registry.config().commonTags("application", applicationName);
	}	
	*/
}
