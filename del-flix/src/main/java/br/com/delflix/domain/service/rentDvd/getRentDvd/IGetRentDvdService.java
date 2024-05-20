package br.com.delflix.domain.service.rentDvd.getRentDvd;

import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

public interface IGetRentDvdService 
{
    public ResponseRentDvdJson GetRentDvd(RequestRentJson request);
}
