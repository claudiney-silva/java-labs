package br.com.effetivo.account.model;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@UserDefinedType("udt_settings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
    private String theme;
    private String mode;
}
