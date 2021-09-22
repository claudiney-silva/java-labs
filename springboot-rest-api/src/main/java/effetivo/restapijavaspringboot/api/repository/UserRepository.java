package effetivo.restapijavaspringboot.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import effetivo.restapijavaspringboot.api.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByFirstName(String firstName);

}