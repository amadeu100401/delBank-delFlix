package br.com.delflix.domain.service.dvdCatalog.deleteDvd.fisicalDelete;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;

public class FisicalDeleteDvdServiceTest {

    @Mock
    private IReadOnlyDvdRepository readOnlyRespository;

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyRespository;

    @InjectMocks
    private FisicalDeleteDvdService fisicalDeleteDvdService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Failed_Service() {
        String identifier = UUID.randomUUID().toString();

        when(readOnlyRespository.DvdExistsByIdentifier(identifier)).thenReturn(false);

        assertThrows(ErrorOnValidationException.class, () -> {
            fisicalDeleteDvdService.DeleteDvd(identifier);
        });
    }
}
