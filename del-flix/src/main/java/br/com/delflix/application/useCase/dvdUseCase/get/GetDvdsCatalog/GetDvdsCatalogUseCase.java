package br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.service.dvdCatalog.getDvd.getDvdCatalog.IGetDvdCatalogService;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;

@Service
public class GetDvdsCatalogUseCase implements IGetDvdsCatalogUseCase
{
    @Autowired
    private final IGetDvdCatalogService _getDvdCatalogService;

    public GetDvdsCatalogUseCase(IGetDvdCatalogService getDvdCatalogService) 
    {
        _getDvdCatalogService = getDvdCatalogService;
    }

    @Override
    public ResponseDvdsCatalogJson Execute() 
    {
        return _getDvdCatalogService.GetDvdCatalog();
    }
}
