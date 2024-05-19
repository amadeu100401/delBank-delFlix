package br.com.delflix.domain.repository.dvdRepository;

import br.com.delflix.domain.entity.Dvd;

public interface IWriteOnlyDvdRepository 
{
    public void SaveDvd(Dvd dvd); 
}
