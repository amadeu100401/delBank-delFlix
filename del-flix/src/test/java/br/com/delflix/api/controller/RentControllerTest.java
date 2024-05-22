package br.com.delflix.api.controller;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.delflix.application.useCase.rentUseCase.getRent.IRentDvdUseCase;
import br.com.delflix.application.useCase.rentUseCase.returnDvd.IReturnDvdUseCase;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class RentControllerTest {

    @Mock
    private IRentDvdUseCase rentDvdUseCase;

    @Mock
    private IReturnDvdUseCase returnDvdUseCase;

    @InjectMocks
    private RentController rentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful Rent")
    public void Successful_Rent() {
        RequestRentJson request = RequestBuilderFactory.RequestRentBuilder();

        ResponseRentDvdJson response = ResponseBuilderFactory.ResponseRentDvdBuilder();
        response.setIdentifier(request.getDvdIdentifier());

        when(rentDvdUseCase.Execute(request)).thenReturn(response);

        ResponseEntity<ResponseRentDvdJson> result = rentController.RentDvd(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(rentDvdUseCase).Execute(request);
    }

    @Test
    @DisplayName("Failed Rent")
    public void Failed_Rent_Invalid_Identifier() {

        RequestRentJson request = RequestBuilderFactory.RequestRentBuilder();
        request.setDvdIdentifier(null);

        doThrow(ErrorOnValidationException.class).when(rentDvdUseCase).Execute(request);

        assertThrows(ErrorOnValidationException.class, () -> {
            rentController.RentDvd(request);
        });

        verify(rentDvdUseCase).Execute(request);
    }

    @Test
    @DisplayName("Successful Return")
    public void Successful_Return_Dvd() {
        String identifier = UUID.randomUUID().toString();

        ResponseEntity result = rentController.ReturnDvd(identifier);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(returnDvdUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Failed Return")
    public void Failed_Rent_Invalid_Return() {

        RequestRentJson request = RequestBuilderFactory.RequestRentBuilder();
        request.setDvdIdentifier(null);

        doThrow(ErrorOnValidationException.class).when(rentDvdUseCase).Execute(request);

        assertThrows(ErrorOnValidationException.class, () -> {
            rentController.RentDvd(request);
        });
        verify(rentDvdUseCase).Execute(request);
    }
}
