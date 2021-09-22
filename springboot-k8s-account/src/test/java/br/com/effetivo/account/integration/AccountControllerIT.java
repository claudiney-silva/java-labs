package br.com.effetivo.account.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.effetivo.account.fixtures.AccountHelper;
import br.com.effetivo.account.model.Account;
import br.com.effetivo.account.testcontainer.config.ContainerEnvironment;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIT extends ContainerEnvironment {

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("When create a account expect sucessful")
    @Test
    public void whenCreateAccountExpectSucessful() {
        Account accountToBeSaved = AccountHelper.getFilledAccount();

        webTestClient.post()
            .uri("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)            
            .body(BodyInserters.fromValue(accountToBeSaved))
            .exchange()
            .expectStatus().is2xxSuccessful()            
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(response -> {
                assertTrue(response.getResponseBody()!=null);
            });
    }    
}
