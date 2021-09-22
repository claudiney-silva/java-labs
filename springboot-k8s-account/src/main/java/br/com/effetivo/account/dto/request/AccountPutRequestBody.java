package br.com.effetivo.account.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Expected format to update an account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPutRequestBody {
  
    @Schema(name = "name", description = "Name of the user", example = "John")
    @NotEmpty(message = "Name is mandatory")
    private String name;
  
    @Schema(name = "bio", description = "Biography of the user", example = "Computer Science student.")
    @NotEmpty(message = "Bio is mandatory")
    private String bio; 

    @Valid
    private SettingsRequestBody settings;    
    
}
