package effetivo.oauth2.springbootkeycloak.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import effetivo.oauth2.springbootkeycloak.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductWebClient {
	@Autowired
	private WebClient webClient;

	public Flux<Product> getProducts() {
		return webClient
				.get()
				.uri("http://localhost:8081/products")
				//.header("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.0.3705;)")
        		.retrieve()
        		.bodyToFlux(Product.class);
	}
	
	public Flux<Product> getProductsUsingAuthorizedClient(OAuth2AuthorizedClient authorizedClient) {
		return webClient
				.get()
        		.uri("http://localhost:8081/products")
				//.header("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.0.3705;)")
        		.retrieve()
        		.bodyToFlux(Product.class);				
	}    

	public Mono<String> getStringUsingAuthorizedClient(OAuth2AuthorizedClient authorizedClient) {
		return webClient.get()
        .uri("http://localhost:8081/products/test")
		//.header("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.0.3705;)")		
        .retrieve()
        /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                clientResponse -> Mono.empty())*/
        .bodyToMono(String.class);
	}   	
}

