package br.com.delflix.domain.service.dvdCatalog.updateDvd;

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
import org.modelmapper.ModelMapper;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import utils.builders.EntityBuilderFactory;
import utils.builders.RequestBuilderFactory;

public class UpdateDvdServiceTest {

    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyDvdRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UpdateDvdService updateDvdService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_Service() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var identifier = UUID.randomUUID().toString();
        var dvd = EntityBuilderFactory.DvdBuilder();
        dvd.setTitle(request.getTitle());
        dvd.setCopiesQuantity(request.getCopiesQuantity());

        when(readOnlyDvdRepository.DvdExistsByIdentifier(identifier)).thenReturn(true);
        when(updateOnlyDvdRepository.GetDvdInDb(identifier)).thenReturn(dvd);
        when(mapper.map(request, Dvd.class)).thenReturn(dvd);
        doNothing().when(updateOnlyDvdRepository).UpdateDvd(dvd);

        var response = updateDvdService.UpdateDvd(request, identifier);

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getCopiesQuantity(), response.getCopiesQuantity());
    }

    @Test
    public void Failed_Service() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var identifier = UUID.randomUUID().toString();

        when(readOnlyDvdRepository.DvdExistsByIdentifier(identifier)).thenReturn(false);
        
        assertThrows(ErrorOnValidationException.class, () -> {
            updateDvdService.UpdateDvd(request, identifier);
        });
    }
}
