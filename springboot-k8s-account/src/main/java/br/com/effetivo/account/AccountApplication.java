package br.com.effetivo.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(
		version = "${myapp.version}",
		title = "${spring.application.name}",		
		description = "${myapp.description}",
		termsOfService = "Terms of Service",
		contact = @Contact(
			name = "${myapp.author}",
			email = "${myapp.email}",
			url = "${myapp.url}"
		),
		license = @License(
			name = "Apache License Version 2.0",			
			url = "https://www.apache.org/license.html"
		)
	)
)
@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
