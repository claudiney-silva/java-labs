package br.com.effetivo.account.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.effetivo.account.testcontainer.config.ContainerEnvironment;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerIT extends ContainerEnvironment  {

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("When get root uri expect response OK")
    @Test
    public void whenGetRootUriExpectResponseOK() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/").build())
            .exchange()
            .expectStatus().isOk();
    }

    @DisplayName("When get api uri expect response OK")
    @Test
    public void whenGetApiUriExpectResponseOK() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api").build())
            .exchange()
            .expectStatus().isOk();
    }    
}
