package br.com.delflix.api.controller;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.delflix.application.useCase.dvdUseCase.delete.fisical.IDeleteDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.delete.logical.ILogicalDeleteUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog.IGetDvdsCatalogUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.getDvd.IGetDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.register.IRegisterDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.update.IUpdateDvdUseCase;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;
import utils.builders.RequestBuilderFactory;
import utils.builders.ResponseBuilderFactory;

public class DvdControllerTest {

    @Mock
    private IRegisterDvdUseCase registerDvdUseCase;

    @Mock
    private IUpdateDvdUseCase updateDvdUseCase;

    @Mock
    private ILogicalDeleteUseCase logicalDeleteUseCase;

    @Mock
    private IDeleteDvdUseCase deleteDvdUseCase;

    @Mock
    private IGetDvdsCatalogUseCase getDvdsCatalogUseCase;

    @Mock
    private IGetDvdUseCase getDvdUseCase;

    @InjectMocks
    private DvdController dvdController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful Dvd Register")
    public void Successful_Dvd_Register() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var response = ResponseBuilderFactory.ResponseDvdBuilder();

        when(registerDvdUseCase.Execute(request)).thenReturn(response);

        ResponseEntity<ResponseDvdJson> result = dvdController.Register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(registerDvdUseCase).Execute(request);
    }

    @Test
    @DisplayName("Failed Dvd Register")
    public void Failed_Dvd_Register() {
        var request = RequestBuilderFactory.RequestDvdBuilder();
        doThrow(ErrorOnValidationException.class).when(registerDvdUseCase).Execute(request);

        assertThrows(ErrorOnValidationException.class, () -> {
            var result = dvdController.Register(request);
            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        });

        verify(registerDvdUseCase).Execute(request);
    }

    @Test
    @DisplayName("Successful Get Dvds Catalog")
    public void Successful_Get_Dvds_Catalog() {
        var response = ResponseBuilderFactory.ResponseDvdsCatalogBuilder();

        when(getDvdsCatalogUseCase.Execute()).thenReturn(response);

        ResponseEntity<ResponseDvdsCatalogJson> result = dvdController.GetDvdsCatalog();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(getDvdsCatalogUseCase).Execute();
    }

    @Test
    @DisplayName("Successful Get Dvds Catalog")
    public void Successful_Get_One_Dvd() {
        String identifier = UUID.randomUUID().toString();
        var response = ResponseBuilderFactory.ResponseDvdBuilder();

        when(getDvdUseCase.Execute(identifier)).thenReturn(response);

        ResponseEntity<ResponseDvdJson> result = dvdController.getMethodName(identifier);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(getDvdUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Failed Get One Dvd")
    public void Failed_Get_One_Dvd() {
        String identifier = UUID.randomUUID().toString();
        doThrow(ErrorOnValidationException.class).when(getDvdUseCase).Execute(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            var result = dvdController.getMethodName(identifier);
            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        });

        verify(getDvdUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Successful Dvd Update")
    public void Successful_Dvd_Update() {
        String identifier = UUID.randomUUID().toString();
        var request = RequestBuilderFactory.RequestDvdBuilder();
        var response = ResponseBuilderFactory.ResponseDvdBuilder();

        when(updateDvdUseCase.Execute(request, identifier)).thenReturn(response);

        ResponseEntity<ResponseDvdJson> result = dvdController.UpdateDvd(identifier, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(updateDvdUseCase).Execute(request, identifier);
    }

    @Test
    @DisplayName("Failed Dvd Update")
    public void Failed_Dvd_Update() {
        String identifier = UUID.randomUUID().toString();
        var request = RequestBuilderFactory.RequestDvdBuilder();

        doThrow(ErrorOnValidationException.class).when(updateDvdUseCase).Execute(request, identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            var result = dvdController.UpdateDvd(identifier, request);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        });

        verify(updateDvdUseCase).Execute(request, identifier);
    }

    @Test
    @DisplayName("Successful Dvd Disable")
    public void Successful_Disable_Dvd() {
        String identifier = UUID.randomUUID().toString();

        var result = dvdController.DisableDvd(identifier);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(logicalDeleteUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Failed Dvd Disable")
    public void Failed_Disable_Dvd() {
        String identifier = UUID.randomUUID().toString();
        doThrow(ErrorOnValidationException.class).when(logicalDeleteUseCase).Execute(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            var result = dvdController.DisableDvd(identifier);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        });

        verify(logicalDeleteUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Successful Delete Dvd")
    public void Successful_Delete_Dvd() {
        String identifier = "someIdentifier";

        var result = dvdController.DeleteMethodName(identifier);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteDvdUseCase).Execute(identifier);
    }

    @Test
    @DisplayName("Failed Delete Dvd")
    public void Failed_Delete_Dvd() {
        String identifier = UUID.randomUUID().toString();
        doThrow(ErrorOnValidationException.class).when(deleteDvdUseCase).Execute(identifier);

        assertThrows(ErrorOnValidationException.class, () -> {
            var result = dvdController.DeleteMethodName(identifier);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        });

        verify(deleteDvdUseCase).Execute(identifier);
    }
}
