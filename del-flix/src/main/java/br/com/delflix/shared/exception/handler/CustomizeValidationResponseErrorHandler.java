package br.com.delflix.shared.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomizeValidationResponseErrorHandler 
{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleBadRequestExceptions(MethodArgumentNotValidException ex) 
    {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return errors;
    }
}
