package br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.delflix.domain.service.dvdCatalog.getDvd.getDvdCatalog.IGetDvdCatalogService;
import utils.builders.ResponseBuilderFactory;

public class GetDvdsCatalogUseCaseTest {

    @Mock
    private IGetDvdCatalogService getDvdsCatalogService;

    @InjectMocks
    private GetDvdsCatalogUseCase getDvdsCatalogUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Success_UseCase() {
        var expectedResponse = ResponseBuilderFactory.ResponseDvdsCatalogBuilder();

        when(getDvdsCatalogService.GetDvdCatalog()).thenReturn(expectedResponse);

        var response = getDvdsCatalogUseCase.Execute();

        assertEquals(expectedResponse, response);
        verify(getDvdsCatalogService, times(1)).GetDvdCatalog();
    }

    @Test
    public void No_Content_UseCase() {
        when(getDvdsCatalogService.GetDvdCatalog()).thenReturn(null);
        var response = getDvdsCatalogUseCase.Execute();

        assertNull(response);
        verify(getDvdsCatalogService, times(1)).GetDvdCatalog();
    }
}
