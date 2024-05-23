package br.com.delflix.application.useCase.dvdUseCase.activate;

import br.com.delflix.domain.service.dvdCatalog.activateDvd.IActivateDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

public class ActivateDvdUseCaseTest
{
    @Mock
    private IActivateDvdService activateDvdService;

    @InjectMocks
    private ActivateDvdUseCase activateDvdUseCase;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success activate dvd useCase")
    public void Success_UseCase()
    {
        String identifier = UUID.randomUUID().toString();

        activateDvdService.Execute(identifier);

        verify(activateDvdService, times(1)).Execute(identifier);
    }

    @Test
    @DisplayName("Failed activate dvd useCase")
    public void Failed_UseCase()
    {
        String identifier = UUID.randomUUID().toString();

        doThrow(new ErrorOnValidationException("DVD does not exist"))
                .when(activateDvdService).Execute(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            activateDvdService.Execute(identifier);
        });

        verify(activateDvdService, times(1)).Execute(identifier);
    }
}
