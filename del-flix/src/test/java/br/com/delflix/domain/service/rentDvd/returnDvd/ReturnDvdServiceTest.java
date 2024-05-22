package br.com.delflix.domain.service.rentDvd.returnDvd;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import utils.builders.EntityBuilderFactory;

public class ReturnDvdServiceTest {

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyDvdRepository;

    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @InjectMocks
    private ReturnDvdService returnDvdService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Return Dvd Service")
    public void Success_Service() {
        var identifier = UUID.randomUUID().toString();
        var dvd = EntityBuilderFactory.DvdBuilder();

        when(readOnlyDvdRepository.DvdExistsByIdentifier(identifier))
                .thenReturn(true);
        when(readOnlyDvdRepository.GetDvdByIdentifier(identifier))
                .thenReturn(dvd);
        doNothing().when(updateOnlyDvdRepository)
                .UpdateDvd(dvd);

        returnDvdService.ReturnDvd(identifier);

        verify(readOnlyDvdRepository).DvdExistsByIdentifier(identifier);
        verify(readOnlyDvdRepository).GetDvdByIdentifier(identifier);
        verify(updateOnlyDvdRepository).UpdateDvd(dvd);
    }

    @Test
    @DisplayName("Fail Return Dvd Service")
    public void Fail_Service() {
        var identifier = UUID.randomUUID().toString();

        when(readOnlyDvdRepository.DvdExistsByIdentifier(identifier)).thenReturn(false);
        assertThrows(ErrorOnValidationException.class, () -> {
            returnDvdService.ReturnDvd(identifier);
        });

        verify(readOnlyDvdRepository).DvdExistsByIdentifier(identifier);
    }
}
