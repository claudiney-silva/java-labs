package br.com.effetivo.account.dto.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Expected format to account settings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingsRequestBody {

    @Schema(name = "theme", description = "Theme of account skin", example = "clouds")
    @NotEmpty(message = "Theme is mandatory")
    private String theme;

    @Schema(name = "mode", description = "Mode of theme", example = "dark")
    @NotEmpty(message = "Mode is mandatory")
    private String mode;    
}
