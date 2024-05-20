package br.com.delflix.domain.service.rentDvd.returnDvd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;

@Service
public class ReturnDvdService implements IReturnDvdService
{
    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRepository;

    public ReturnDvdService(IUpdateOnlyDvdRepository updateOnlyRespository,
     IReadOnlyDvdRepository readOnlyRepository) 
     {
        _updateOnlyRespository = updateOnlyRespository;
        _readOnlyRepository = readOnlyRepository;
    }

    @Override
    public void ReturnDvd(String dvdIdentifier) {
        Validate(dvdIdentifier);
        UpdateDvdStatus(dvdIdentifier);
    }
    
    private void Validate(String identifier)
    {
        if(!DvdExists(identifier))
        {
            throw new ErrorOnValidationException("Dvd not found");
        }
    }

    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRepository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private void UpdateDvdStatus(String identifier)
    {
        var dvdInDb = GetDvdInDb(identifier);

        dvdInDb.setAviable(true);
        dvdInDb.setCopiesQuantity(dvdInDb.getCopiesQuantity()+1);

        SaveChanges(dvdInDb);
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _readOnlyRepository.GetDvdByIdentifier(identifier);
    }

    private void SaveChanges(Dvd dvdEntity)
    {
        _updateOnlyRespository.UpdateDvd(dvdEntity);
    }
}
