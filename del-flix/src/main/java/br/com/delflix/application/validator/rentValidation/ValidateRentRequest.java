package br.com.delflix.application.validator.rentValidation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.ValidationResult;

@Service
public class ValidateRentRequest implements IValidateRentRequest
{
    @Override
    public ValidationResult ValidateRequest(RequestRentJson request) 
    {
        final Validator<RequestRentJson> validator = new RequestRentValidation();
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
