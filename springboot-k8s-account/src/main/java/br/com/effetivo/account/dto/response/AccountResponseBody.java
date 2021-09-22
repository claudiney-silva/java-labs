package br.com.effetivo.account.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseBody {

    @Schema(name = "id", description = "Id of account", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
  
    @Schema(name = "name", description = "Name of the user", example = "John")
    private String name;

    @Schema(name = "bio", description = "Biography of the user", example = "Computer Science student.")
    private String bio; 

    private CredentialsResponseBody credentials;
  
    private SettingsResponseBody settings;        
}
