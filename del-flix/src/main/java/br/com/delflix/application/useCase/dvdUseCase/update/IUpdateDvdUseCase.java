package br.com.delflix.application.useCase.dvdUseCase.update;

import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IUpdateDvdUseCase 
{
    public ResponseDvdJson Execute(RequestDvdJson request, String guid);
}
