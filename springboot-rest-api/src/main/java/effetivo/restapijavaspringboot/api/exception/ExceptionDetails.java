package effetivo.restapijavaspringboot.api.exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected int status;
    protected String title;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}
