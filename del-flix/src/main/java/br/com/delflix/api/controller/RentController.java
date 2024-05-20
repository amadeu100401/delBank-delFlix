package br.com.delflix.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.delflix.application.useCase.rentUseCase.getRent.IRentDvdUseCase;
import br.com.delflix.application.useCase.rentUseCase.returnDvd.IReturnDvdUseCase;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rent/v1")
@Tag(name = "Rent", description = "Rent API")
public class RentController {

    @Autowired
    private final IRentDvdUseCase _rentDvdUseCase;

    @Autowired
    private final IReturnDvdUseCase _returnDvdUseCase;

    public RentController(IRentDvdUseCase rentDvdUseCase,
            IReturnDvdUseCase returnDvdUseCase) {
        _rentDvdUseCase = rentDvdUseCase;
        _returnDvdUseCase = returnDvdUseCase;
    }

    @PostMapping("/rent-dvd")
    @Operation(summary = "Rent dvd", description = "Rent dvd", tags = {"Rent"}, responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseRentDvdJson.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<ResponseRentDvdJson> RentDvd(@RequestBody RequestRentJson request) {
        var response = _rentDvdUseCase.Execute(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/return-dvd/{identifier}")
    @Operation(summary = "Return dvd", description = "Return dvd", tags = {"Rent"}, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity ReturnDvd(@PathVariable String identifier) {
        _returnDvdUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }
}
