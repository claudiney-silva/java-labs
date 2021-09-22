package br.com.effetivo.webflux.service;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    private final Product user = ProductCreator.createValidProduct();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
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
    @DisplayName("findAll returns a flux of product")
    public void findAll_ReturnFluxOfProduct_WhenSuccessful() {
        StepVerifier.create(productService.findAll())
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns a Mono with product when it exists")
    public void findById_ReturnMonoProduct_WhenSuccessful() {
        StepVerifier.create(productService.findById(1))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono error when product does not exist")
    public void findById_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.findById(1))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("save creates an product when successful")
    public void save_CreatesProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        StepVerifier.create(productService.save(userToBeSaved))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("saveAll creates a list of product when successful")
    public void saveAll_CreatesListOfProduct_WhenSuccessful() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        StepVerifier.create(productService.saveAll(List.of(userToBeSaved, userToBeSaved)))
                .expectSubscription()
                .expectNext(user, user)
                .verifyComplete();
    }

    @Test
    @DisplayName("saveAll returns Mono error when one of the objects in the list contains empty or null name")
    public void saveAll_ReturnsMonoError_WhenContainsInvalidName() {
        Product userToBeSaved = ProductCreator.createProductToBeSaved();

        BDDMockito.when(productRepositoryMock
                        .saveAll(ArgumentMatchers.anyIterable()))
                .thenReturn(Flux.just(user, user.withName("")));

        StepVerifier.create(productService.saveAll(List.of(userToBeSaved, userToBeSaved.withName(""))))
                .expectSubscription()
                .expectNext(user)
                .expectError(ResponseStatusException.class)
                .verify();
    }


    @Test
    @DisplayName("delete removes the product when successful")
    public void delete_RemovesProduct_WhenSuccessful() {
        StepVerifier.create(productService.delete(1))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("delete returns Mono error when anome does not exist")
    public void delete_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.delete(1))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("update save updated product and returns empty mono when successful")
    public void update_SaveUpdatedProduct_WhenSuccessful() {
        StepVerifier.create(productService.update(ProductCreator.createValidProduct()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update returns Mono error when product does not exist")
    public void update_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.update(ProductCreator.createValidProduct()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

}