package br.com.delflix.shared.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorOnValidationException extends RuntimeException 
{
    public ErrorOnValidationException(String message) {
        super(message);
    }

    public ErrorOnValidationException(List<String> errorsMessage) 
    {
        super(FormatErrorMessage(errorsMessage));
    }

    private static String FormatErrorMessage(List<String> errorsMessage) {
        return "Validation failed. The following errors were found:\n" +
               errorsMessage.stream()
                           .map(error -> "- " + error)
                           .collect(Collectors.joining("\n"));
    }
}
