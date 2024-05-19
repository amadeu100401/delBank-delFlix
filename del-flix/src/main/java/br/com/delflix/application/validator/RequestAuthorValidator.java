package br.com.delflix.application.validator;

import static java.util.function.Predicate.not;

import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.fluentvalidator.AbstractValidator;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

public class RequestAuthorValidator extends AbstractValidator<RequestAuthorRegisterJson>
{
    @Override
    public void rules() {
        ruleFor(RequestAuthorRegisterJson::getName)
            .must(not(nullValue()))
            .withMessage("Name field is required");
            
        ruleFor(RequestAuthorRegisterJson::getSurname)
            .must(not(nullValue()))
            .withMessage("Surname field is required");
    }

}
