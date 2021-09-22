package br.com.effetivo.account.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Embedded;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("app_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @PrimaryKey
  private UUID id;

  private String name;

  private String bio; 

  @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
  private Credentials credentials;

  private Settings settings;
  
}
