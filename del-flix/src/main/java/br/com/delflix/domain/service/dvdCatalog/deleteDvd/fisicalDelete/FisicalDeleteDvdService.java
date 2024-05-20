package br.com.delflix.domain.service.dvdCatalog.deleteDvd.fisicalDelete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;

@Service
public class FisicalDeleteDvdService implements IFisicalDeleteDvdService
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    public FisicalDeleteDvdService(IReadOnlyDvdRepository readOnlyRespository,
    IUpdateOnlyDvdRepository updateOnlyRespository) 
    {
        _readOnlyRespository = readOnlyRespository;
        _updateOnlyRespository = updateOnlyRespository;
    }

    @Override
    public void DeleteDvd(String identifier) 
    {
        ValidateIdentifier(identifier);
        UpdateDvd(identifier);
    }
    
    private void ValidateIdentifier(String identifier)
    {
        if(!DvdExists(identifier))
        {
            throw new ErrorOnValidationException("Dvd not found");
        }
    }

    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private void UpdateDvd(String identifier)
    {
        _updateOnlyRespository.FisicalDelete(identifier);
    }
}
