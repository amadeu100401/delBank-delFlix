package br.com.delflix.application.useCase.dvdUseCase.get.getDvd;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.service.dvdCatalog.getDvd.getOneDvd.IGetOneDvdService;
import utils.builders.ResponseBuilderFactory;

public class GetDvdUseCaseTest {

    @Mock
    private IGetOneDvdService getOneDvdService;

    @InjectMocks
    private GetDvdUseCase getDvdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Get One Dvd")
    public void Success_UseCase() {
        var identifierRequest = UUID.randomUUID().toString();
        var expectedResponse = ResponseBuilderFactory.ResponseDvdBuilder();

        when(getOneDvdService.GetOneDvd(identifierRequest)).thenReturn(expectedResponse);

        var response = getDvdUseCase.Execute(identifierRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("No Content Get One Dvd")
    public void No_Content_UseCase() {
        var identifierRequest = UUID.randomUUID().toString();

        when(getOneDvdService.GetOneDvd(identifierRequest)).thenReturn(null);

        var response = getDvdUseCase.Execute(identifierRequest);

        assertNull(response);
    }
}
