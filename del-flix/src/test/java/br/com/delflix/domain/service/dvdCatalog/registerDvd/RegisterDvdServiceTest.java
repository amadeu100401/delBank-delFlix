package br.com.delflix.domain.service.dvdCatalog.registerDvd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IWriteOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import utils.builders.EntityBuilderFactory;
import utils.builders.RequestBuilderFactory;

public class RegisterDvdServiceTest {

    @Mock
    private IWriteOnlyDvdRepository writeOnlyDvdRepository;

    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RegisterDvdService registerDvdService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Register Dvd Service")
    public void Success_Service() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var dvd = EntityBuilderFactory.DvdBuilder();
        dvd.setTitle(request.getTitle());
        dvd.setCopiesQuantity(request.getCopiesQuantity());

        when(readOnlyDvdRepository.DvdAlreadyRegistred(request.getTitle())).thenReturn(false);
        when(mapper.map(request, Dvd.class)).thenReturn(dvd);
        doNothing().when(writeOnlyDvdRepository).SaveDvd(dvd);

        var response = registerDvdService.RegisterDvd(request);

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getCopiesQuantity(), response.getCopiesQuantity());
    }

    @Test
    @DisplayName("Fail Register Dvd Service")
    public void Fail_Service() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        when(readOnlyDvdRepository.DvdAlreadyRegistred(request.getTitle())).thenReturn(true);
        assertThrows(ErrorOnValidationException.class, () -> {
            registerDvdService.RegisterDvd(request);
        });
    }
}
