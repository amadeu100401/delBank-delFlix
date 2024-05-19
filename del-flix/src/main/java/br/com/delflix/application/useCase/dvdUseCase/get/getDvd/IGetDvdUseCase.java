package br.com.delflix.application.useCase.dvdUseCase.get.getDvd;

import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IGetDvdUseCase 
{
    public ResponseDvdJson Execute(String identifier);
}
