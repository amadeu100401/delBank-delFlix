package br.com.delflix.application.validator.rentValidation;

import static java.util.function.Predicate.not;

import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.fluentvalidator.AbstractValidator;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

public class RequestRentValidation extends AbstractValidator<RequestRentJson>
{
    @Override
    public void rules() 
    {
        ruleFor(RequestRentJson::getDvdIdentifier)
            .must(not(nullValue()))
                .withMessage("Dvd identifier field is required")
                .withFieldName("dvdIdentifier");

        ruleFor(RequestRentJson::getDays)
            .must(not(nullValue()))
                .withMessage("Days field is required")
                .withFieldName("days")
            .must(greaterThanOrEqual(1))
                .withMessage("Days quantity must be greater than or equal to 1")
                .withFieldName("Days");
    }

}
