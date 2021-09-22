package br.com.effetivo.account.fixtures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.effetivo.account.testcontainer.config.ContainerEnvironment;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CassandraTemplateSampleTest extends ContainerEnvironment {
    
    @Autowired
    CassandraOperations cassandraTemplate;

    @DisplayName("When create table with cql CassandraTemplate expect sucessful")
    @Test
    public void createTable() {
        String cql = "CREATE TABLE app_customer (id uuid, name text, email set<text>, branch list<text>, options map<text, text>, PRIMARY KEY (id));";
        Assertions.assertTrue(cassandraTemplate.getCqlOperations().execute(cql));
    }

    @DisplayName("When insert a rowe with cql CassandraTemplate expect sucessful")
    @Test
    public void insertTable() {
        String cql = "INSERT INTO app_customer(id, name, email, branch, options) VALUES (uuid(), 'John', {'john@doe.com', 'test@email.com'}, ['A', 'B', 'C'], {'monday':'something', 'tuesday':'another'});";
        Assertions.assertTrue(cassandraTemplate.getCqlOperations().execute(cql));
    }

}
