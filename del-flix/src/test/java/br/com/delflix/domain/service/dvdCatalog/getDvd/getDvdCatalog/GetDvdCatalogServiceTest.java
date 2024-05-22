package br.com.delflix.domain.service.dvdCatalog.getDvd.getDvdCatalog;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import utils.builders.EntityBuilderFactory;

public class GetDvdCatalogServiceTest {

    @Mock
    private IReadOnlyDvdRepository readOnlyDvdRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private GetDvdCatalogService getDvdCatalogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Success Get Dvd Catalog Service")
    public void Success_Service() {
        var dvd = EntityBuilderFactory.DvdBuilder();

        when(readOnlyDvdRepository.GetDvdsCatalog()).thenReturn(List.of(dvd));

        var response = getDvdCatalogService.GetDvdCatalog();
        assertEquals(1, response.getDvdsCatalog().size());
    }

    @Test
    @DisplayName("No Content Get Dvd Catalog Service")
    public void No_Content_Service() {
        when(readOnlyDvdRepository.GetDvdsCatalog()).thenReturn(List.of());

        var response = getDvdCatalogService.GetDvdCatalog();

        assertEquals(0, response.getDvdsCatalog().size());
    }

}
