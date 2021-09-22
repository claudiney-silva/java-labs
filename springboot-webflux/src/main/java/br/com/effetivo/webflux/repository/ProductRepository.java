package br.com.effetivo.webflux.repository;

import br.com.effetivo.webflux.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    Mono<Product> findById(int id);
}
