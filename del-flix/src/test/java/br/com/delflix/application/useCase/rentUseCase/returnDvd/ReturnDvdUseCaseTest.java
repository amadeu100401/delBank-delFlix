package br.com.delflix.application.useCase.rentUseCase.returnDvd;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.service.rentDvd.returnDvd.IReturnDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;

public class ReturnDvdUseCaseTest {

    @Mock
    private IReturnDvdService returnDvdService;

    @InjectMocks
    private ReturnDvdUseCase returnDvdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_UseCase() {
        var identifier = UUID.randomUUID().toString();

        returnDvdUseCase.Execute(identifier);

        verify(returnDvdService, times(1)).ReturnDvd(identifier);
    }

    @Test
    public void Failed_UseCase() {
        var identifier = UUID.randomUUID().toString();

        doThrow(new ErrorOnValidationException("Dvd not found"))
                .when(returnDvdService).ReturnDvd(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            returnDvdUseCase.Execute(identifier);;
        });

        verify(returnDvdService, times(1)).ReturnDvd(identifier);
    }
}
