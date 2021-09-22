package br.com.effetivo.account.exception.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  @Schema(name = "status", description = "HTTP status code", example = "422")
  private final int status;

  @Schema(name = "message", description = "Error message", example = "Validation error. Check 'validationErrors' field for details.")
  private final String message;
  
  @Schema(name = "stackTrace", description = "Stack Trace of error")
  private String stackTrace;

  @Schema(name = "validationErrors", description = "Validation errors list")
  private List<ValidationError> validationErrors;

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {

      @Schema(name = "field", description = "Field name", example = "name")
      private final String field;

      @Schema(name = "message", description = "Error message", example = "Name is mandatory")
      private final String message;
    }

    public void addValidationError(String field, String message){
      if(Objects.isNull(validationErrors)){
        validationErrors = new ArrayList<>();
      }
      validationErrors.add(new ValidationError(field, message));
    }
}