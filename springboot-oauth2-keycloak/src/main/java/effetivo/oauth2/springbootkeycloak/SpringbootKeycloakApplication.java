package effetivo.oauth2.springbootkeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKeycloakApplication.class, args);
	}

}

// @Component
// @AllArgsConstructor
// class Runner implements ApplicationRunner {
// 	WebClient webClient;

// 	@Override
// 	public void run(ApplicationArguments args) throws Exception {
// 		Product[] p = webClient
// 			.get()
// 			.uri("http://localhost:8081/client/products")
// 			.retrieve()
// 			.bodyToMono(Product[].class)
// 			.block();
// 			//.subscribe(System.out::println);

// 		System.out.println(p[0].getTitle());
// 	}
// }
