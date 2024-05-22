package br.com.delflix.domain.service.dvdCatalog.deleteDvd.logicalDelete;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import utils.builders.EntityBuilderFactory;

public class LogicalDeleteDvdServiceTest {

    @Mock
    private IReadOnlyDvdRepository readOnlyRespository;

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyRespository;

    @InjectMocks
    private LogicalDeleteDvdService logicalDeleteDvdService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_Service() {
        String identifier = UUID.randomUUID().toString();
        var dvd = EntityBuilderFactory.DvdBuilder();

        when(readOnlyRespository.DvdExistsByIdentifier(identifier)).thenReturn(true);
        when(updateOnlyRespository.GetDvdInDb(identifier)).thenReturn(dvd);
        doNothing().when(updateOnlyRespository).UpdateDvd(dvd);

        logicalDeleteDvdService.DeleteDvd(identifier);

        assertEquals(dvd.isAviable(), false);
    }

    @Test
    public void Fail_Service() {
        String identifier = UUID.randomUUID().toString();
        when(readOnlyRespository.DvdExistsByIdentifier(identifier)).thenReturn(false);
        assertThrows(ErrorOnValidationException.class, () -> {
            logicalDeleteDvdService.DeleteDvd(identifier);
        });
    }
}
