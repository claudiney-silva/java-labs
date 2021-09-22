package br.com.effetivo.webflux.repository;

import br.com.effetivo.webflux.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByUsername(String username);
}