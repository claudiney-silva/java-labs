package br.com.effetivo.cassandra.springbootcassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import br.com.effetivo.cassandra.springbootcassandra.domain.Product;

public interface ProductRepository extends CassandraRepository<Product, Long> {
    
}
