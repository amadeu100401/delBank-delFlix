package br.com.delflix.application.validator;

import static java.util.function.Predicate.not;

import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.fluentvalidator.AbstractValidator;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

public class RegisterDvdValidation extends AbstractValidator<RequestDvdJson>
{
    @Override
    public void rules() 
    {
        ruleFor(RequestDvdJson::getTitle)            
            .must(not(nullValue()))
            .withMessage("Title field is required");
        
        ruleFor(RequestDvdJson::getAuthor)
            .must(not(nullValue()))
            .withMessage("Author field is not valid");
            
        ruleFor(RequestDvdJson::getGender)
            .must(not(nullValue()))
            .withMessage("Gender field is required");

        ruleFor(RequestDvdJson::getPublished)
            .must(not(nullValue()))
            .withMessage("Published field is required");

        ruleFor(RequestDvdJson::getCopiesQuantity)
            .must(not(nullValue()))
            .withMessage("copies quantity field is required")
            .withFieldName("copiesQuantity")
            .must(greaterThanOrEqual(1))
            .withMessage("copies quantity must be greater than or equal to 1")
            .withFieldName("copiesQuantity");

        ruleFor(RequestDvdJson::isAviable)
            .must(not(nullValue()))
            .withMessage("Aviable field is required")
            .withFieldName("Aviable");
    }
}
