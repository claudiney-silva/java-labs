package br.com.effetivo.webflux.controller;

import br.com.effetivo.webflux.domain.Product;
import br.com.effetivo.webflux.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
@Slf4j
@RequiredArgsConstructor
@SecurityScheme(
        name = "Basic Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all products",
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Flux<Product> listAll() {
        return productService.findAll();
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Mono<Product> findById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Mono<Product> save(@Valid @RequestBody Product user) {
        return productService.save(user);
    }

    @PostMapping("batch")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Flux<Product> saveBatch(@RequestBody List<Product> users) {
        return productService.saveAll(users);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Mono<Void> update(@PathVariable int id, @Valid @RequestBody Product user) {
        return productService.update(user.withId(id));
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            security = @SecurityRequirement(name = "Basic Authentication"),
            tags = {"products"})
    public Mono<Void> delete(@PathVariable int id) {
        return productService.delete(id);
    }

}
