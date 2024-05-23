package br.com.delflix.api.controller;

import br.com.delflix.application.useCase.dvdUseCase.activate.IActivateDvdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.delflix.application.useCase.dvdUseCase.delete.fisical.IDeleteDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.delete.logical.ILogicalDeleteUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog.IGetDvdsCatalogUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.getDvd.IGetDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.register.IRegisterDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.update.IUpdateDvdUseCase;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/dvd/v1")
@Tag(name = "Dvds", description = "Dvds API")
public class DvdController {
    @Autowired
    private final IRegisterDvdUseCase _registerDvdUseCase;

    @Autowired
    private final IUpdateDvdUseCase _updateDvdUseCase;

    @Autowired
    private final ILogicalDeleteUseCase _logicalDeleteUseCase;

    @Autowired
    private final IDeleteDvdUseCase _deleteUseCase;

    @Autowired
    private final IGetDvdsCatalogUseCase _dvdsCatalogUseCase;

    @Autowired
    private final IGetDvdUseCase _getDvdUseCase;

    @Autowired
    private final IActivateDvdUseCase _activateDvdUseCase;

    public DvdController(IRegisterDvdUseCase registerDvdUseCase,
            IUpdateDvdUseCase updateDvdUseCase,
            ILogicalDeleteUseCase logicalDeleteUseCase,
            IDeleteDvdUseCase deleteUseCase,
            IGetDvdsCatalogUseCase dvdsCatalogUseCase,
            IGetDvdUseCase getDvdUseCase,
             IActivateDvdUseCase activateDvdUseCase) {
        _registerDvdUseCase = registerDvdUseCase;
        _updateDvdUseCase = updateDvdUseCase;
        _logicalDeleteUseCase = logicalDeleteUseCase;
        _deleteUseCase = deleteUseCase;
        _dvdsCatalogUseCase = dvdsCatalogUseCase;
        _getDvdUseCase = getDvdUseCase;
        _activateDvdUseCase = activateDvdUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Register dvd", description = "Register dvd", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "201", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDvdJson.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ResponseDvdJson> Register(@RequestBody RequestDvdJson request) {
        var response = _registerDvdUseCase.Execute(request);

        return ResponseEntity.created(null).body(response);
    }

    @Cacheable("dvds")
    @GetMapping("/dvd")
    @Operation(summary = "Get dvds", description = "Get dvds", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDvdsCatalogJson.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ResponseDvdsCatalogJson> GetDvdsCatalog() {
        var response = _dvdsCatalogUseCase.Execute();
        return ResponseEntity.ok(response);
    }

    @Cacheable("dvdInfo")
    @GetMapping("/dvd/{identifier}")
    @Operation(summary = "Get dvds by identifier", description = "Get dvds by identifier", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDvdJson.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ResponseDvdJson> getMethodName(@PathVariable String identifier) {
        var response = _getDvdUseCase.Execute(identifier);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{identifier}")
    @Operation(summary = "Update dvd's info", description = "Update dvd's info", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDvdJson.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ResponseDvdJson> UpdateDvd(@PathVariable String identifier, @RequestBody RequestDvdJson request) {
        var response = _updateDvdUseCase.Execute(request, identifier);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/disable-dvd/{identifier}")
    @Operation(summary = "Desactivate dvd", description = "Desactivate dvd", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity DisableDvd(@PathVariable String identifier) {
        _logicalDeleteUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{identifier}")
    @Operation(summary = "Delete dvd", description = "Delete dvd", tags = {"Dvds"}, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity DeleteMethodName(@PathVariable String identifier) {
        _deleteUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/activate-dvd/{identifier}")
    @Operation(summary = "Activate dvd", description = "Activate dvd", tags = {"Dvds"}, responses = {
            @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity ActivateDvd(@PathVariable String identifier)
    {
        _activateDvdUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }
}
