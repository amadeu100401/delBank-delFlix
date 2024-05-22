package br.com.delflix.application.useCase.dvdUseCase.update;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.application.validator.dvdValidation.IValidateDvdRequest;
import br.com.delflix.domain.service.dvdCatalog.updateDvd.IUpdateDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.fluentvalidator.context.ValidationResult;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class UpdateDvdUseCaseTest {

    @Mock
    private IValidateDvdRequest validateRequest;

    @Mock
    private IUpdateDvdService updateDvdService;

    @InjectMocks
    private UpdateDvdUseCase updateDvdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_UseCase() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var identifier = UUID.randomUUID().toString();
        var expectedResponse = ResponseBuilderFactory.ResponseDvdBuilder();

        ValidationResult validationResult = ValidationResult.ok();

        when(validateRequest.ValidateDvdRequest(request)).thenReturn(validationResult);
        when(validateRequest.ValidateRequestAuthor(request.getAuthor())).thenReturn(validationResult);

        when(updateDvdService.UpdateDvd(request, identifier)).thenReturn(expectedResponse);

        var actualResponse = updateDvdUseCase.Execute(request, identifier);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void Failed_UseCase() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var identifier = UUID.randomUUID().toString();

        ValidationResult validationResult = ValidationResult.fail(null);

        when(validateRequest.ValidateDvdRequest(request)).thenReturn(validationResult);
        when(validateRequest.ValidateRequestAuthor(request.getAuthor())).thenReturn(validationResult);
        when(updateDvdUseCase.Execute(request, identifier)).thenThrow(new ErrorOnValidationException(""));

        ErrorOnValidationException exception = assertThrows(ErrorOnValidationException.class, () -> {
            updateDvdUseCase.Execute(request, identifier);
        });
    }
}
