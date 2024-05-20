package br.com.delflix.application.useCase.dvdUseCase.get.getDvd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.service.dvdCatalog.getDvd.getOneDvd.IGetOneDvdService;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class GetDvdUseCase implements IGetDvdUseCase
{
    @Autowired
    private final IGetOneDvdService _getOneDvdService;

    public GetDvdUseCase(IGetOneDvdService getOneDvdService)
    {
        _getOneDvdService = getOneDvdService;
    }

    @Override
    public ResponseDvdJson Execute(String identifier) 
    {    
        return _getOneDvdService.GetOneDvd(identifier);
    }
}
