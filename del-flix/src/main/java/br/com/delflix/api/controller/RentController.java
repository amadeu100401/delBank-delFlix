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


@RestController
@RequestMapping("/api/rent/v1")
public class RentController 
{
    @Autowired
    private final IRentDvdUseCase _rentDvdUseCase;

    @Autowired
    private final IReturnDvdUseCase _returnDvdUseCase;

    public RentController(IRentDvdUseCase rentDvdUseCase,
    IReturnDvdUseCase returnDvdUseCase) 
    {
        _rentDvdUseCase = rentDvdUseCase;
        _returnDvdUseCase = returnDvdUseCase;
    }

    @PostMapping("/rent-dvd")
    public ResponseEntity<ResponseRentDvdJson> RentDvd(@RequestBody RequestRentJson request) 
    {
        var response = _rentDvdUseCase.Execute(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/return-dvd/{identifier}")
    public ResponseEntity ReturnDvd(@PathVariable String identifier) 
    {   
        _returnDvdUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }
}
