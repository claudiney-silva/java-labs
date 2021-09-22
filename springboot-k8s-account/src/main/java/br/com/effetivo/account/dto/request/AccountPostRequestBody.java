package br.com.effetivo.account.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Expected format to create an account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPostRequestBody {

    @Schema(name = "name", description = "Name of the user", example = "John")
    @NotEmpty(message = "Name is mandatory")
    private String name;
  
    @Schema(name = "bio", description = "Biography of the user", example = "Computer Science student.")
    @NotEmpty(message = "Bio is mandatory")
    private String bio; 

    @Valid
    private CredentialsRequestBody credentials;
  
    @Valid
    private SettingsRequestBody settings;    
    
}
