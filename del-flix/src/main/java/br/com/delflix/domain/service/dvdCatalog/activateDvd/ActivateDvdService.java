package br.com.delflix.domain.service.dvdCatalog.activateDvd;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivateDvdService implements IActivateDvdService
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyDvdRepository;

    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyDvdRepository;

    public ActivateDvdService(IReadOnlyDvdRepository readOnlyDvdRepository, IUpdateOnlyDvdRepository updateOnlyDvdRepository)
    {
        _readOnlyDvdRepository = readOnlyDvdRepository;
        _updateOnlyDvdRepository = updateOnlyDvdRepository;
    }

    @Override
    public void Execute(String identifier)
    {
        Validate(identifier);
        ActivateDvdInDb(identifier);

    }

    private void Validate(String identifier)
    {
        if(DvdNotExistis(identifier))
        {
            throw new ErrorOnValidationException("DVD does not exist");
        }
    }

    private boolean DvdNotExistis(String identifier)
    {
        return  !_readOnlyDvdRepository.DvdExistsByIdentifier(identifier);
    }

    private void ActivateDvdInDb(String identifier)
    {
        var dvdEntity = GetDvdInDb(identifier);

        if(dvdEntity.isAviable())
        {
            return;
        }

        dvdEntity.setAviable(true);

        SaveChange(dvdEntity);
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _readOnlyDvdRepository.GetDvdByIdentifier(identifier);
    }

    private void SaveChange(Dvd data)
    {
        _updateOnlyDvdRepository.UpdateDvd(data);
    }
}
