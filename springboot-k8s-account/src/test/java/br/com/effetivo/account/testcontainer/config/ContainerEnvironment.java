package br.com.effetivo.account.testcontainer.config;

import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import br.com.effetivo.account.testcontainer.container.CassandraTestContainer;

@Testcontainers
public class ContainerEnvironment {
    @Container
    public static CassandraContainer<CassandraTestContainer> cassandraContainer
        = CassandraTestContainer.getInstance();
}
