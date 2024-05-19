package br.com.delflix.shared.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.delflix.shared.exception.ErrorExceptionResponse;
import br.com.delflix.shared.exception.ErrorOnValidationException;

@ControllerAdvice
@RestController
public class CustomizeResponseErrorEntity extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorExceptionResponse exceptionResponse = new ErrorExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorOnValidationException.class)
    public final ResponseEntity<ErrorExceptionResponse> handleBadRequestExceptions(ErrorOnValidationException ex, WebRequest request) {
        ErrorExceptionResponse exceptionResponse = new ErrorExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
