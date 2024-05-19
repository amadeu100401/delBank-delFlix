package br.com.delflix.infra.repository.dvd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IWriteOnlyDvdRepository;

@Service
public class DvdRepository implements IWriteOnlyDvdRepository, IReadOnlyDvdRepository, IUpdateOnlyDvdRepository
{
    @Autowired
    private final IDvdRepository _dvdRepository;

    public DvdRepository(IDvdRepository dvdRepository) 
    {
        _dvdRepository = dvdRepository;
    }

    @Override
    public void SaveDvd(Dvd dvd) 
    {
        _dvdRepository.save(dvd);
    }

    @Override
    public boolean DvdAlreadyRegistred(String title) 
    {
        Dvd result = _dvdRepository.findByTitle(title);
        
        return result!= null;
    }

    @Override
    public boolean DvdExistsByIdentifier(String identifier) 
    {
        Dvd result = _dvdRepository.findByIdentifier(identifier);

        return result != null;
    }

    @Override
    public void UpdateDvd(Dvd dvd) 
    {
        _dvdRepository.save(dvd);
    }

    @Override
    public Dvd GetDvdInDb(String identifier)
    {
        return _dvdRepository.findByIdentifier(identifier);
    }

    @Override
    public void LogicalDeleteDvd(Dvd dvdEntity) 
    {
        _dvdRepository.save(dvdEntity);
    }

    @Override
    public void FisicalDelete(String identifier) 
    {
        _dvdRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<Dvd> GetDvdsCatalog() 
    {
        return _dvdRepository.findAll();
    }
}
