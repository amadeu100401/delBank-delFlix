package br.com.delflix.domain.service.dvdCatalog.getDvd.getOneDvd;

import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IGetOneDvdService 
{
    public ResponseDvdJson GetOneDvd(String identifier);
}
