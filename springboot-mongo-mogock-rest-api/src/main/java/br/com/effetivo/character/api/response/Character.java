package br.com.effetivo.character.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    @Schema(description = "The unique ID of the character resource.", example="60d5460283f33f68d2dcf19d", required = true)
    private String id;

    @Schema(description = "The name of the character.", example="Test Name", required = true)
    private String name;
}
