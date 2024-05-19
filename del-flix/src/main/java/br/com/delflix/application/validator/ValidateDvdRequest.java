package br.com.delflix.application.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.ValidationResult;

@Service
public class ValidateDvdRequest implements IValidateDvdRequest
{
    @Override
    public ValidationResult ValidateDvdRequest(RequestDvdJson request)
    {
        final Validator<RequestDvdJson> validator = new RegisterDvdValidation();
        final ValidationResult result = validator.validate(request);

        return result;
    }

    @Override
    public ValidationResult ValidateRequestAuthor(RequestAuthorRegisterJson request)
    {
        final Validator<RequestAuthorRegisterJson> validator = new RequestAuthorValidator();
        final ValidationResult result = validator.validate(request);

        return result;
    }

    @Override
    public List<String> GetErrorMessage(List<String> errors, ValidationResult result)
    {   
        errors.addAll(result.getErrors()
            .stream()
            .map(error -> error.getMessage())
            .collect(Collectors.toList()));
        return errors;
    }

    @Override
    public boolean IsValidRequest(ValidationResult result)
    {
        return result.isValid();
    }

}
