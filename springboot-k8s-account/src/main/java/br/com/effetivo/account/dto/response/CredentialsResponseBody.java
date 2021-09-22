package br.com.effetivo.account.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Credentials info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsResponseBody {

    @Schema(name = "username", description = "Username to login", example = "john")
    private String username;

}
