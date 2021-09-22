package br.com.effetivo.webflux.service;

import br.com.effetivo.webflux.domain.Product;
import br.com.effetivo.webflux.repository.ProductRepository;
import io.netty.util.internal.StringUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(int id) {
        return productRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public Mono<Product> save(Product user) {
        return productRepository.save(user);
    }

    @Transactional
    public Flux<Product> saveAll(List<Product> users) {
        return productRepository.saveAll(users)
                .doOnNext(this::throwResponseStatusExceptionWhenEmptyName);
    }

    private void throwResponseStatusExceptionWhenEmptyName(Product user){
        if(StringUtil.isNullOrEmpty(user.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Name");
        }
    }

    public Mono<Void> update(Product user) {
        return findById(user.getId())
                .flatMap(productRepository::save)
                .then();
    }

    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(productRepository::delete);
    }
}