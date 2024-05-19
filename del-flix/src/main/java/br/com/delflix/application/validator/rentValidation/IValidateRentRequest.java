package br.com.delflix.application.validator.rentValidation;

import java.util.List;

import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.fluentvalidator.context.ValidationResult;

public interface IValidateRentRequest 
{
    public ValidationResult ValidateRequest(RequestRentJson request);

    public List<String> GetErrorMessage(List<String> errors, ValidationResult result);

    public boolean IsValidRequest(ValidationResult result);
}
