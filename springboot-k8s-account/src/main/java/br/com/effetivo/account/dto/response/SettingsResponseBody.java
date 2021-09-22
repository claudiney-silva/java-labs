package br.com.effetivo.account.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Set of account settings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingsResponseBody {
    @Schema(name = "theme", description = "Theme of account skin", example = "clouds")
    private String theme;

    @Schema(name = "mode", description = "Mode of theme", example = "dark")
    private String mode;   
}