package br.com.delflix.domain.service.dvdCatalog.registerDvd;

import br.com.delflix.shared.request.DdvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

public interface IRegisterDvdService 
{
    public ResponseDvdJson RegisterDvd(RequestDvdJson request);
}
