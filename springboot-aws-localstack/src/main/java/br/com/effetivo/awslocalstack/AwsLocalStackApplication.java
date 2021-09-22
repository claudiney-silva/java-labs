package br.com.effetivo.awslocalstack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AwsLocalStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLocalStackApplication.class, args);
	}

}
