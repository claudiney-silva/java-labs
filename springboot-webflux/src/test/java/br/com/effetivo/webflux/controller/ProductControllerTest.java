package br.com.effetivo.webflux.controller;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import br.com.effetivo.webflux.domain.Product;
import br.com.effetivo.webflux.service.ProductService;
import br.com.effetivo.webflux.util.ProductCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productServiceMock;

    private final Product user = ProductCreator.createValidProduct();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(productServiceMock.findAll())
                .thenReturn(Flux.just(user));

        BDDMockito.when(productServiceMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(user));

        BDDMockito.when(productServiceMock.save(ProductCreator.createProductToBeSaved()))
                .thenReturn(Mono.just(user));

        BDDMockito.when(productServiceMock
                        .saveAll(List.of(ProductCreator.createProductToBeSaved(), ProductCreator.createProductToBeSaved())))
                .thenReturn(Flux.just(user, user));

        BDDMockito.when(productServiceMock.delete(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        BDDMockito.when(productServiceMock.update(ProductCreator.createValidProduct()))
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
    @DisplayName("listAll returns a flux of products")
    public void listAll_ReturnFluxOfProduct_WhenSuccessful() {
        StepVerifier.create(productController.listAll())
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns a Mono with products when it exists")
    public void findById_ReturnMonoProduct_WhenSuccessful() {
        StepVerifier.create(productController.findById(1))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("save creates an products when successful")
    public void save_CreatesProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        StepVerifier.create(productController.save(userToBeSaved))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("saveBatch creates a list of products when successful")
    public void saveBatch_CreatesListOfProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        StepVerifier.create(productController.saveBatch(List.of(userToBeSaved, userToBeSaved)))
                .expectSubscription()
                .expectNext(user, user)
                .verifyComplete();
    }

    @Test
    @DisplayName("delete removes the products when successful")
    public void delete_RemovesProduct_WhenSuccessful() {
        StepVerifier.create(productController.delete(1))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update save updated products and returns empty mono when successful")
    public void update_SaveUpdatedProduct_WhenSuccessful() {
        StepVerifier.create(productController.update(1, ProductCreator.createValidProduct()))
                .expectSubscription()
                .verifyComplete();
    }
}