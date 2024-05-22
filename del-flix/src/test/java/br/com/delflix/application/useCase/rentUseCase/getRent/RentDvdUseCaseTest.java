package br.com.delflix.application.useCase.rentUseCase.getRent;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.application.validator.rentValidation.IValidateRentRequest;
import br.com.delflix.domain.service.rentDvd.getRentDvd.IGetRentDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;
import br.com.fluentvalidator.context.ValidationResult;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class RentDvdUseCaseTest {

    @Mock
    private IValidateRentRequest validateRequest;

    @Mock
    private IGetRentDvdService getRentDvdService;

    @InjectMocks
    private RentDvdUseCase rentDvdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Get Rent Dvd")
    public void Success_UseCase() {
        var request = RequestBuilderFactory.RequestRentBuilder();
        var expectedResponse = ResponseBuilderFactory.ResponseRentDvdBuilder();

        ValidationResult validationResult = ValidationResult.ok();

        when(validateRequest.ValidateRequest(request)).thenReturn(validationResult);
        when(getRentDvdService.GetRentDvd(request)).thenReturn(expectedResponse);

        ResponseRentDvdJson actualResponse = rentDvdUseCase.Execute(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Failed Get Rent Dvd")
    public void Failed_UseCase() {
        var request = RequestBuilderFactory.RequestRentBuilder();
        List<String> errorMessages = new ArrayList<>();

        ValidationResult validationResult = ValidationResult.fail(null);

        when(validateRequest.ValidateRequest(request)).thenReturn(validationResult);

        ErrorOnValidationException exception = assertThrows(ErrorOnValidationException.class, () -> {
            rentDvdUseCase.Execute(request);
        });
    }
}
