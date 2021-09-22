package effetivo.restapijavaspringboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import effetivo.restapijavaspringboot.api.domain.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Auth findByUsername(String username);
    
}
