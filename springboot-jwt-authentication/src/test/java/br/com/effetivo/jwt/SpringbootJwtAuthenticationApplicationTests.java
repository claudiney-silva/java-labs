package br.com.effetivo.jwt;

import br.com.effetivo.jwt.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT//,
		//classes = { },
		//properties = { "spring.main.allow-bean-definition-overriding=true" }
)
//@Import(PostgresTestConfiguration.class)
@Testcontainers
class SpringbootJwtAuthenticationApplicationTests {

	@Container
	private PostgreSQLContainer container = new PostgreSQLContainer(
			DockerImageName.parse("postgres:latest"))
			.withDatabaseName("jwt-db")
            .withUsername("admin")
            .withPassword("admin");

	@Test
	void contextLoads() {
	}

}
