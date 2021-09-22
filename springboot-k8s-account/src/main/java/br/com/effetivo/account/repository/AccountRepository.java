package br.com.effetivo.account.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import br.com.effetivo.account.model.Account;

public interface AccountRepository extends CassandraRepository<Account, UUID> {
    List<Account> findByNameStartingWith(final String nameMatch);
}
