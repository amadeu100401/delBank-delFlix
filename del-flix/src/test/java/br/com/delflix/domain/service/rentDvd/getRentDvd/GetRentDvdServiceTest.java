package br.com.delflix.domain.service.rentDvd.getRentDvd;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.modelmapper.ModelMapper;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.domain.service.dvdCatalog.updateDvd.IUpdateDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;
import utils.builders.EntityBuilderFactory;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class GetRentDvdServiceTest {

    @Mock
    private IUpdateDvdService updateDvdService;

    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @Mock
    private IUpdateOnlyDvdRepository updateOnlyDvdRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private GetRentDvdService getRentDvdService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Get Rent Dvd Service")
    public void Success_Service() {
        var request = RequestBuilderFactory.RequestRentBuilder();
        var dvd = EntityBuilderFactory.DvdBuilder();

        var expectedResponse = ResponseBuilderFactory.ResponseRentDvdBuilder();
        expectedResponse.setTitle(dvd.getTitle());
        expectedResponse.setIdentifier(request.getDvdIdentifier());

        when(readOnlyDvdRepository.DvdExistsByIdentifier(request.getDvdIdentifier())).thenReturn(true);
        when(readOnlyDvdRepository.GetDvdByIdentifier(request.getDvdIdentifier())).thenReturn(dvd);
        when(mapper.map(request, ResponseRentDvdJson.class)).thenReturn(expectedResponse);
        doNothing().when(updateOnlyDvdRepository).UpdateDvd(dvd);

        var response = getRentDvdService.GetRentDvd(request);

        assertEquals(response, expectedResponse);

        verify(readOnlyDvdRepository).DvdExistsByIdentifier(request.getDvdIdentifier());
    }

    @Test
    @DisplayName("Fail Get Rent Dvd Service")
    public void Fail_Service() {
        var request = RequestBuilderFactory.RequestRentBuilder();
        when(readOnlyDvdRepository.DvdExistsByIdentifier(request.getDvdIdentifier())).thenReturn(false);
        assertThrows(ErrorOnValidationException.class, () -> {
            getRentDvdService.GetRentDvd(request);
        });
        verify(readOnlyDvdRepository).DvdExistsByIdentifier(request.getDvdIdentifier());
    }
}
