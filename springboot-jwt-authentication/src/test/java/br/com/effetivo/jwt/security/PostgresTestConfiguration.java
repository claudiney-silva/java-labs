package br.com.effetivo.jwt.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class PostgresTestConfiguration {

    @Bean
    PostgreSQLContainer postgres() {
        PostgreSQLContainer container = new PostgreSQLContainer(
            DockerImageName.parse("postgres:latest"))
            .withDatabaseName("jwt-db")
            .withUsername("admin")
            .withPassword("admin");

        System.out.println("//////////////////////////////////"+container.getHost());

        container.start();
        return container;
    }
}
