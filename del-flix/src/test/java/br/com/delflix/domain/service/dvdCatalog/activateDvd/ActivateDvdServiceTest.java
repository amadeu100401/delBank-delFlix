package br.com.delflix.domain.service.dvdCatalog.activateDvd;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ActivateDvdServiceTest
{
    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyDvdRepository;

    @InjectMocks
    private ActivateDvdService activateDvdService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Failed activate dvd service")
    public void Success_Service()
    {
        String identifier = UUID.randomUUID().toString();

        when(readOnlyDvdRepository.DvdExistsByIdentifier(identifier))
                .thenReturn(false);


        assertThrows(ErrorOnValidationException.class, () -> {
            activateDvdService.Execute(identifier);
        });
    };
}
