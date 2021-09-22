package br.com.effetivo.account.dto.request;

import javax.validation.constraints.NotEmpty;

import br.com.effetivo.account.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Expected format to account credentials")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsRequestBody {

    @Schema(name = "username", description = "Username to login", example = "john")
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @Schema(name = "password", description = "Secure password", example = "Tes23%$ok")
    @ValidPassword
    private String password;   
    
}
