package br.com.delflix.domain.repository.dvdRepository;

import br.com.delflix.domain.entity.Dvd;

public interface IUpdateOnlyDvdRepository 
{
    public void UpdateDvd(Dvd dvd);
    public Dvd GetDvdInDb(String identifier);
    public void LogicalDeleteDvd(Dvd dvdEntity);
    public void FisicalDelete(String identifier);
}
