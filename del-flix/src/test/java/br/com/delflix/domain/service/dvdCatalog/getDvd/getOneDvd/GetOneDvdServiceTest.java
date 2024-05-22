package br.com.delflix.domain.service.dvdCatalog.getDvd.getOneDvd;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import utils.builders.EntityBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class GetOneDvdServiceTest {

    @Mock
    private IReadOnlyDvdRepository readOnlyRespository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private GetOneDvdService getOneDvdService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Get One Dvd Service")
    public void Success_Service() {
        String identifier = UUID.randomUUID().toString();
        var dvd = EntityBuilderFactory.DvdBuilder();

        var expectedResponse = ResponseBuilderFactory.ResponseDvdBuilder();
        expectedResponse.setIdentifier(identifier);
        expectedResponse.setTitle(dvd.getTitle());
        expectedResponse.setCopiesQuantity(dvd.getCopiesQuantity());

        when(readOnlyRespository.DvdExistsByIdentifier(identifier)).thenReturn(true);
        when(readOnlyRespository.GetDvdByIdentifier(identifier)).thenReturn(dvd);
        when(mapper.map(dvd, ResponseDvdJson.class)).thenReturn(expectedResponse);

        var response = getOneDvdService.GetOneDvd(identifier);

        assertEquals(expectedResponse.getIdentifier(), response.getIdentifier());
        assertEquals(expectedResponse.getTitle(), response.getTitle());
        assertEquals(expectedResponse.getCopiesQuantity(), response.getCopiesQuantity());
    }

    @Test
    @DisplayName("Fail Get One Dvd Service")
    public void Fail_Service() {
        String identifier = UUID.randomUUID().toString();

        when(readOnlyRespository.DvdExistsByIdentifier(identifier)).thenReturn(false);

        assertThrows(ErrorOnValidationException.class, () -> {
            getOneDvdService.GetOneDvd(identifier);
        });
    }
}
