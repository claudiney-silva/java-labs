package br.com.effetivo.account.exception.handler;

import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.effetivo.account.exception.NotFoundException;
import br.com.effetivo.account.exception.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String TRACE = "trace";

  @Value("${server.error.include-stacktrace:never}")
  private String printStackTrace;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.UNPROCESSABLE_ENTITY.value(), 
        "Validation error. Check 'validationErrors' field for details."
    );
    
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
        errorResponse.addValidationError(
          fieldError.getField(), 
          fieldError.getDefaultMessage()
        );
    }
    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(
      NotFoundException exception, 
      WebRequest request
  ){
    return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleAllUncaughtException(
      RuntimeException exception, 
      WebRequest request
  ){
    return buildErrorResponse(
        exception,
        "Unknown error occurred", 
        HttpStatus.INTERNAL_SERVER_ERROR, 
        request
    );
  }

  @Override
  public ResponseEntity<Object> handleExceptionInternal(
      Exception exception,
      Object body,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    return buildErrorResponse(exception,status,request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception,
      HttpStatus httpStatus,
      WebRequest request
  ) {
    return buildErrorResponse(
        exception, 
        exception.getMessage(), 
        httpStatus, 
        request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception,
      String message,
      HttpStatus httpStatus,
      WebRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(
        httpStatus.value(), 
        exception.getMessage()
    );
    
    if(!printStackTrace.equalsIgnoreCase("NEVER") && isTraceOn(request)){
      errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
    }
    return ResponseEntity.status(httpStatus).body(errorResponse);
  }

  private boolean isTraceOn(WebRequest request) {
    String [] value = request.getParameterValues(TRACE);
    return Objects.nonNull(value)
        && value.length > 0
        && value[0].contentEquals("true");
  }    

}
