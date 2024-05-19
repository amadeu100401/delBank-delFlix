package br.com.delflix.shared.exception;

import java.util.Date;

public class ErrorExceptionResponse 
{
    private final Date TimeStamp;
    private final String ErrorMessage;
    private final String Details;

    public ErrorExceptionResponse(Date timeStamp, String errorMessage, String details) {
        TimeStamp = timeStamp;
        ErrorMessage = errorMessage;
        Details = details;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getDetails() {
        return Details;
    }   
}
