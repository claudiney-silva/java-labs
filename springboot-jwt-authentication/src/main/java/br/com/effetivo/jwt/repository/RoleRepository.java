package br.com.effetivo.jwt.repository;

import java.util.Optional;

import br.com.effetivo.jwt.model.ERole;
import br.com.effetivo.jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
