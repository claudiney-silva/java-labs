package effetivo.restapijavaspringboot.api.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import effetivo.restapijavaspringboot.api.exception.BadRequestException;
import effetivo.restapijavaspringboot.api.exception.BadRequestExceptionDetails;
import effetivo.restapijavaspringboot.api.exception.ExceptionDetails;
import effetivo.restapijavaspringboot.api.exception.ValidationExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException exception) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder().timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value()).title("Bad Request Exception, Check the Documentation")
                        .details(exception.getMessage()).developerMessage(exception.getClass().getName()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationExceptionDetails exDetails = ValidationExceptionDetails.builder().timestamp(LocalDateTime.now())
                .status(status.value()).title("Bad Request Exception, Invalid Fields")
                .details("Check the field(s) error(s)").developerMessage(ex.getClass().getName()).fields(fields)
                .fieldsMessage(fieldsMessage).build();

        return new ResponseEntity<>(exDetails, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ExceptionDetails exDetails = ExceptionDetails.builder().timestamp(LocalDateTime.now()).status(status.value())
                .title(ex.getCause().getMessage()).details(ex.getMessage()).developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(exDetails, headers, status);
    }
}
