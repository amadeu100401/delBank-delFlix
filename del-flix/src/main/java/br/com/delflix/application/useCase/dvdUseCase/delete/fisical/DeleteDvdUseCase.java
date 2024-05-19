package br.com.delflix.application.useCase.dvdUseCase.delete.fisical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;

@Service
public class DeleteDvdUseCase implements IDeleteDvdUseCase
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;
    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    public DeleteDvdUseCase(IReadOnlyDvdRepository readOnlyRespository, IUpdateOnlyDvdRepository updateOnlyRespository) 
     {
        _readOnlyRespository = readOnlyRespository;
        _updateOnlyRespository = updateOnlyRespository;
    }

    @Override
    public void Execute(String identifier) 
    {
        ValidateIdentifier(identifier);
        DeleteDvd(identifier);
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

    private void DeleteDvd(String identifier)
    {
        _updateOnlyRespository.FisicalDelete(identifier);
    }
}
