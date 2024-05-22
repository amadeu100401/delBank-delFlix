package br.com.delflix.application.useCase.dvdUseCase.register;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.application.validator.dvdValidation.IValidateDvdRequest;
import br.com.delflix.domain.service.dvdCatalog.registerDvd.IRegisterDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.fluentvalidator.context.ValidationResult;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class RegisterDvdUseCaseTest {

    @Mock
    private IValidateDvdRequest validateRequest;

    @Mock
    private IRegisterDvdService registerDvdService;

    @InjectMocks
    private RegisterDvdUseCase registerDvdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_UseCase() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var expectedResponse = ResponseBuilderFactory.ResponseDvdBuilder();

        ValidationResult validationResult = ValidationResult.ok();

        when(validateRequest.ValidateDvdRequest(request)).thenReturn(validationResult);
        when(validateRequest.ValidateRequestAuthor(request.getAuthor())).thenReturn(validationResult);
        when(registerDvdService.RegisterDvd(request)).thenReturn(expectedResponse);

        var actualResponse = registerDvdUseCase.Execute(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void Failed_UseCase() {
        var request = RequestBuilderFactory.RequestDvdBuilder();

        when(validateRequest.ValidateDvdRequest(request)).thenReturn(ValidationResult.fail(null));
        when(validateRequest.ValidateRequestAuthor(request.getAuthor())).thenReturn(ValidationResult.fail(null));
        when(registerDvdService.RegisterDvd(request)).thenThrow(new ErrorOnValidationException(""));

        assertThrows(ErrorOnValidationException.class, () -> {
            registerDvdUseCase.Execute(request);
        });
    }

}
