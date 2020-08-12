package mk.training.exception.handle;

import mk.training.exception.ExceptionResponse;
import mk.training.exception.InvalidJwtAuthenticationException;
import mk.training.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
    ExceptionResponse exr = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exr, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequest(Exception ex, WebRequest request) {
    ExceptionResponse exr = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exr, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidJwtAuthenticationException.class)
  public final ResponseEntity<ExceptionResponse> handleFailAuthentication(Exception ex, WebRequest request) {
    ExceptionResponse exr = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(exr, HttpStatus.BAD_REQUEST);
  }
}
