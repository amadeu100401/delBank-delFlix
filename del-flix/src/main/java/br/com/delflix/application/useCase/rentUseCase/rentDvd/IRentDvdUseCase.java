package br.com.delflix.application.useCase.rentUseCase.rentDvd;

import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

public interface IRentDvdUseCase 
{
    public ResponseRentDvdJson Execute(RequestRentJson request);
}
