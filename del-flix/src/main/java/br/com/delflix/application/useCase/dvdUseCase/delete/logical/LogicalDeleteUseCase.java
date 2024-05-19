package br.com.delflix.application.useCase.dvdUseCase.delete.logical;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;

@Service
public class LogicalDeleteUseCase implements ILogicalDeleteUseCase
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;
    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    public LogicalDeleteUseCase(IReadOnlyDvdRepository readOnlyRespository,
    IUpdateOnlyDvdRepository updateOnlyRespository) 
     {
        _readOnlyRespository = readOnlyRespository;
        _updateOnlyRespository = updateOnlyRespository;
    }

    @Override
    public void Execute(String identifier) 
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
       var dvdInDb = GetDvdInDb(identifier);

       dvdInDb.setAviable(false);
       dvdInDb.setUpdateAt(new Date());

       _updateOnlyRespository.UpdateDvd(dvdInDb);
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _updateOnlyRespository.GetDvdInDb(identifier);
    }

}
