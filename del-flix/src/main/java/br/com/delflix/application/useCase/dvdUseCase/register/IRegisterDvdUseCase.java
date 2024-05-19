package br.com.delflix.application.useCase.dvdUseCase.register;

import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IRegisterDvdUseCase 
{
    public ResponseDvdJson Execute(RequestDvdJson request);
}
