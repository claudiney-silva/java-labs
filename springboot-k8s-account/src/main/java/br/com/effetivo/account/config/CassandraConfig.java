package br.com.effetivo.account.config;

import java.time.Duration;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspaceName;  

  @Value("${spring.data.cassandra.schema-action}")
  private String schemaAction;

  @Value("${spring.data.cassandra.contact-points}")
  private String contactPoints;

  @Value("${spring.data.cassandra.local-datacenter}")
  private String localDataCenter;

  @Value("${spring.data.cassandra.username}")
  private String username;

  @Value("${spring.data.cassandra.password}")
  private String password;

  @Value("${spring.data.cassandra.port}")
  private Integer port;  

  @Value("${spring.data.cassandra.entity-base-package}")
  private String entityBasePackages;     

  @Override
  public String getKeyspaceName() {
    return keyspaceName;
  }
      
  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.valueOf(schemaAction);
  }  

  @Override
  public String getContactPoints() {
    return contactPoints;
  }

  @Override
  public String getLocalDataCenter() {
    return localDataCenter;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public String[] getEntityBasePackages() {
    return new String[] { entityBasePackages };
  }

  @Bean
  @Override
  public CqlSessionFactoryBean cassandraSession() {
      CqlSessionFactoryBean cassandraSession = super.cassandraSession();
      cassandraSession.setUsername(username);
      cassandraSession.setPassword(password);
      return cassandraSession;
  }  

  @Override
  protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
      return new SessionBuilderConfigurer() {
          @Override
          public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
              return cqlSessionBuilder
                      .withConfigLoader(DriverConfigLoader.programmaticBuilder().withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(20000)).build());
          }
      };
  }

}