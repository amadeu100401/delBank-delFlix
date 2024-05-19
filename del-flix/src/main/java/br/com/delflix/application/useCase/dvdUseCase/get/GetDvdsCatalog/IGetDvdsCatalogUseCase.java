package br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog;

import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;

public interface IGetDvdsCatalogUseCase 
{
    public ResponseDvdsCatalogJson Execute();
}
