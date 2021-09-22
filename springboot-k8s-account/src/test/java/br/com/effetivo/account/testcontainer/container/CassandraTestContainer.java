package br.com.effetivo.account.testcontainer.container;

//import java.net.InetSocketAddress;
import java.time.Duration;
//import java.util.List;

import com.datastax.driver.core.Session;
//import com.datastax.oss.driver.api.core.CqlSession;

import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class CassandraTestContainer extends CassandraContainer<CassandraTestContainer> {

    public static final String IMAGE_VERSION = "cassandra:3";

    private static CassandraContainer<CassandraTestContainer> container;

    public CassandraTestContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static CassandraContainer<CassandraTestContainer> getInstance() {
        if (container == null) {
            container = new CassandraTestContainer();
        }
        container
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withExposedPorts(9042);

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("CASSANDRA_USERNAME", container.getUsername());
        System.setProperty("CASSANDRA_PASSWORD", container.getPassword());
        System.setProperty("CASSANDRA_KEYSPACE", "account");
        System.setProperty("CASSANDRA_HOST", container.getHost());
        System.setProperty("CASSANDRA_PORT", container.getMappedPort(9042).toString());
        System.setProperty("CASSANDRA_DC", "datacenter1");

        createKeyspace();

        //InetSocketAddress cassandraContainerAddress = new InetSocketAddress(container.getHost(), container.getMappedPort(9042));

        // Session session = CqlSession.builder()
        //     .addContactPoint(cassandraContainerAddress)
        //     .withLocalDatacenter("datacenter1")
        //     .build();  
    }
 
    @Override
    public void stop() {
    }

    private void createKeyspace() {
        Session session = getInstance().getCluster().connect();
        session.execute("CREATE KEYSPACE IF NOT EXISTS account WITH replication = \n" +
                        "{'class':'SimpleStrategy','replication_factor':'1'};");
    }

    // private void test() {
    //     String keyspace = "keyspace_name";
    //     com.datastax.oss.driver.api.core.CqlSession session = CqlSession
    //             .builder()
    //             .withLocalDatacenter("Cassandra")
    //             .addContactPoints(List.of(new InetSocketAddress("private_ip_node_1", 9042)))
    //             .withAuthCredentials("cassandra","cassandra")
    //             .withKeyspace(keyspace)
    //             .build();

    //     session.execute("query");
    // }
    
}
