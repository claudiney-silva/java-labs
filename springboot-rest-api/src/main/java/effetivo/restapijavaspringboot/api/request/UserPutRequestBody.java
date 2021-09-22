package effetivo.restapijavaspringboot.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPutRequestBody {
    private Long id;

    @Schema(description = "E-mail do usuário", example="john@doe.com", required = true)
    private String email;

    @Schema(description = "Senha do usuário", example="123456789", required = true)
    private String password;

    @Schema(description = "Nome do usuário", example="John", required = true)    
    private String firstName;

    @Schema(description = "Sobrenome do usuário", example="Doe", required = true)
    private String lastName;
}
