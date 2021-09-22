package br.com.effetivo.kafkaavrotestcontainers.config;

import br.com.effetivo.kafkaavrotestcontainers.listener.UserListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserListenerConfig {

    @Bean
    @ConditionalOnProperty(value = "myapp.listener.enabled", havingValue = "true")
    public UserListener userListener() {
        return new UserListener();
    }

}
