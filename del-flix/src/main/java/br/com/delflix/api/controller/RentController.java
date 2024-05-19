package br.com.delflix.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.delflix.application.useCase.rentUseCase.rentDvd.IRentDvdUseCase;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

@RestController
@RequestMapping("/api/rent/v1")
public class RentController {

    private final IRentDvdUseCase rentDvdUseCase;

    @Autowired
    public RentController(IRentDvdUseCase rentDvdUseCase) {
        this.rentDvdUseCase = rentDvdUseCase;
    }

    @PostMapping("/rent-dvd")
    public ResponseEntity<ResponseRentDvdJson> rentDvd(@RequestBody RequestRentJson request) 
    {
        var response = rentDvdUseCase.Execute(request);

        // Verifica se o response está vazio (opcional)
        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
