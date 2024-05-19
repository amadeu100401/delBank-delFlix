package br.com.delflix.shared.response.dvdResponse;

import java.util.List;

public class ResponseDvdsCatalogJson 
{
    private List<ResponseDvdJson> DvdsCatalog;

    public ResponseDvdsCatalogJson() {
    }

    public ResponseDvdsCatalogJson(List<ResponseDvdJson> dvdsCatalog) 
    {
        DvdsCatalog = dvdsCatalog;
    }

    public List<ResponseDvdJson> getDvdsCatalog() 
    {
        return DvdsCatalog;
    }

    public void setDvdsCatalog(List<ResponseDvdJson> dvdsCatalog) 
    {
        DvdsCatalog = dvdsCatalog;
    }
}
