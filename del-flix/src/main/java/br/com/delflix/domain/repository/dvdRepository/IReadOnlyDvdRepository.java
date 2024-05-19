package br.com.delflix.domain.repository.dvdRepository;

import java.util.List;

import br.com.delflix.domain.entity.Dvd;

public interface IReadOnlyDvdRepository 
{
    public boolean DvdAlreadyRegistred(String title);
    public boolean DvdExistsByIdentifier(String identifier);
    public List<Dvd> GetDvdsCatalog();
}
