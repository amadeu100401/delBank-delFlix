package br.com.delflix.domain.service.dvdCatalog.updateDvd;

import br.com.delflix.shared.request.DdvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IUpdateDvdService 
{
    public ResponseDvdJson UpdateDvd(RequestDvdJson request, String identifier);
}
