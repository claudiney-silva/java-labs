package effetivo.oauth2.springbootkeycloak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import effetivo.oauth2.springbootkeycloak.client.ProductWebClient;
import effetivo.oauth2.springbootkeycloak.domain.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductClientController {

    private final ProductWebClient productWebClient;

    @GetMapping("/client")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }     

    @GetMapping("/client/test")
    public ResponseEntity<String> test(@RegisteredOAuth2AuthorizedClient("demo") OAuth2AuthorizedClient authorizedClient) {
        productWebClient.getStringUsingAuthorizedClient(authorizedClient)
        .map(string -> "Retrieved using Client Credentials Grant Type: " + string)
        .subscribe(System.out::println);
        return ResponseEntity.ok("sucesso");
    }     

    @GetMapping("/client/products")
    public Flux<Product> findAll(@RegisteredOAuth2AuthorizedClient("demo") OAuth2AuthorizedClient authorizedClient) {
        return productWebClient.getProductsUsingAuthorizedClient(authorizedClient);
    } 

}
