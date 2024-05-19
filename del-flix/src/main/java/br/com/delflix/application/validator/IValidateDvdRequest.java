package br.com.delflix.application.validator;

import java.util.List;

import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.fluentvalidator.context.ValidationResult;

public interface IValidateDvdRequest 
{
    public ValidationResult ValidateDvdRequest(RequestDvdJson request); 

    public ValidationResult ValidateRequestAuthor(RequestAuthorRegisterJson request);

    public List<String> GetErrorMessage(List<String> errors, ValidationResult result);

    public boolean IsValidRequest(ValidationResult result);
}
