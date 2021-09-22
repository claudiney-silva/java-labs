package br.com.effetivo.orders.config;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Configuration
public class CassandraConfig {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;
  
    @Value("${spring.data.cassandra.local-datacenter}")
    private String dataCenter;
  
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;
  
    @Value("${spring.data.cassandra.username}")
    private String userName;
  
    @Value("${spring.data.cassandra.password}")
    private String password;
  
    @Value("${spring.data.cassandra.port}")
    private Integer port;    

    @Bean
    public CqlSessionFactoryBean getCqlSession() {
      CqlSessionFactoryBean factory = new CqlSessionFactoryBean();
      factory.setUsername(userName);
      factory.setPassword(password);
      factory.setPort(port);
      factory.setKeyspaceName(keyspaceName);
      factory.setContactPoints(contactPoints);
      factory.setLocalDatacenter(dataCenter);
      return factory;
    }    

    @Bean
    @ConditionalOnMissingBean
    public CassandraTemplate cassandraTemplate(CqlSession session) throws Exception {
        return new CassandraTemplate(session);
    }    
}