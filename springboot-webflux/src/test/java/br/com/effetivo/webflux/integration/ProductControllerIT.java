package br.com.effetivo.webflux.integration;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import br.com.effetivo.webflux.domain.Product;
import br.com.effetivo.webflux.repository.ProductRepository;
import br.com.effetivo.webflux.util.ProductCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProductControllerIT {
    private static final String ADMIN_USER = "john";
    private static final String REGULAR_USER = "david";

    @MockBean
    private ProductRepository productRepositoryMock;

    @Autowired
    private WebTestClient client;

    //private WebTestClient testClientUser;
    //private WebTestClient testClientAdmin;
    //private WebTestClient testClientInvalid;

    private final Product user = ProductCreator.createValidProduct();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install(
                builder -> builder.allowBlockingCallsInside("java.util.UUID", "randomUUID")
        );
    }

    @BeforeEach
    public void setUp() {
        //testClientUser = webTestClientUtil.authenticateClient("david","password");
        //testClientAdmin = webTestClientUtil.authenticateClient("john","password");
        //testClientInvalid = webTestClientUtil.authenticateClient("x","x");

        BDDMockito.when(productRepositoryMock.findAll())
                .thenReturn(Flux.just(user));

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(user));

        BDDMockito.when(productRepositoryMock.save(ProductCreator.createProductToBeSaved()))
                .thenReturn(Mono.just(user));

        BDDMockito.when(productRepositoryMock
                        .saveAll(List.of(ProductCreator.createProductToBeSaved(), ProductCreator.createProductToBeSaved())))
                .thenReturn(Flux.just(user, user));

        BDDMockito.when(productRepositoryMock.delete(ArgumentMatchers.any(Product.class)))
                .thenReturn(Mono.empty());

        BDDMockito.when(productRepositoryMock.save(ProductCreator.createValidProduct()))
                .thenReturn(Mono.empty());

    }

    @Test
    public void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("listAll returns unauthorized when user is not authenticated")
    public void listAll_ReturnsUnauthorized_WhenUserIsNotAuthenticated() {
        client
                .get()
                .uri("/products")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("listAll returns forbidden when user is successfully authenticated and does not have role ADMIN")
    @WithUserDetails(REGULAR_USER)
    public void listAll_ReturnForbidden_WhenUserDoesNotHaveRoleAdmin() {
        client
                .get()
                .uri("/products")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("listAll returns a flux of product when user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void listAll_ReturnFluxOfProduct_WhenSuccessful() {
        client
                .get()
                .uri("/products")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(user.getId())
                .jsonPath("$.[0].name").isEqualTo(user.getName());
    }

    @Test
    @DisplayName("listAll returns a flux of product when user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void listAll_Flavor2_ReturnFluxOfProduct_WhenSuccessful() {
        client
                .get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(user);
    }

    @Test
    @DisplayName("findById returns a Mono with product when it exists and user is successfully authenticated and has role USER")
    @WithUserDetails(REGULAR_USER)
    public void findById_ReturnMonoProduct_WhenSuccessful() {
        client
                .get()
                .uri("/products/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(user);
    }

    @Test
    @DisplayName("findById returns Mono error when product does not exist and user is successfully authenticated and has role USER")
    @WithUserDetails(REGULAR_USER)
    public void findById_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        client
                .get()
                .uri("/Products/{id}", 1)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }

    @Test
    @DisplayName("save creates an product when successful and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void save_CreatesProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        client
                .post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userToBeSaved))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(user);
    }

    @Test
    @DisplayName("saveBatch creates a list of product when successful and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void saveBatch_CreatesListOfProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        client
                .post()
                .uri("/products/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(userToBeSaved, userToBeSaved)))
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Product.class)
                .hasSize(2)
                .contains(user);
    }

    @Test
    @DisplayName("saveBatch returns Mono error when one of the objects in the list contains empty or null name and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void saveBatch_ReturnsMonoError_WhenContainsInvalidName() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        BDDMockito.when(productRepositoryMock
                        .saveAll(ArgumentMatchers.anyIterable()))
                .thenReturn(Flux.just(user, user.withName("")));

        client
                .post()
                .uri("/products/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(userToBeSaved, userToBeSaved)))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400);
    }

    @Test
    @DisplayName("save returns mono error with bad request when name is empty and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void save_ReturnsError_WhenNameIsEmpty() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved().withName("");

        client
                .post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userToBeSaved))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400);

    }

    @Test
    @DisplayName("delete removes the product when successful and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void delete_RemovesProduct_WhenSuccessful() {
        client
                .delete()
                .uri("/products/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("delete returns Mono error when product does not exist and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void delete_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        client
                .delete()
                .uri("/products/{id}", 1)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }

    @Test
    @DisplayName("update save updated product and returns empty mono when successful and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void update_SaveUpdatedProduct_WhenSuccessful() {
        client
                .put()
                .uri("/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(user))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("update returns Mono error when product does not exist and user is successfully authenticated and has role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void update_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        client.put()
                .uri("/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(user))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }
}