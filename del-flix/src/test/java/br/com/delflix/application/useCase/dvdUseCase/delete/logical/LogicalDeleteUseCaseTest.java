package br.com.delflix.application.useCase.dvdUseCase.delete.logical;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.service.dvdCatalog.deleteDvd.logicalDelete.ILogicalDeleteDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;

public class LogicalDeleteUseCaseTest {

    @Mock
    private ILogicalDeleteDvdService deleteDvdService;

    @InjectMocks
    private LogicalDeleteUseCase deleteDvdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Logical Delete Dvd")
    public void Success_UseCase() {
        var identifier = UUID.randomUUID().toString();

        deleteDvdUseCase.Execute(identifier);

        verify(deleteDvdService, times(1)).DeleteDvd(identifier);
    }

    @Test
    @DisplayName("Failed Logical Delete Dvd")
    public void Failed_UseCase() {
        var identifier = UUID.randomUUID().toString();

        doThrow(new ErrorOnValidationException("Dvd not found"))
                .when(deleteDvdService).DeleteDvd(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            deleteDvdUseCase.Execute(identifier);
        });

        verify(deleteDvdService, times(1)).DeleteDvd(identifier);
    }
}
