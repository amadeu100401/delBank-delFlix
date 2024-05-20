package br.com.delflix.application.useCase.dvdUseCase.delete.fisical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.service.dvdCatalog.deleteDvd.fisicalDelete.IFisicalDeleteDvdService;

@Service
public class DeleteDvdUseCase implements IDeleteDvdUseCase
{
    @Autowired
    private final IFisicalDeleteDvdService _fisicalDeleteDvdService;

    public DeleteDvdUseCase(IFisicalDeleteDvdService fisicalDeleteDvdService) 
     {
        _fisicalDeleteDvdService = fisicalDeleteDvdService;
    }

    @Override
    public void Execute(String identifier) 
    {
        _fisicalDeleteDvdService.DeleteDvd(identifier);
    }
}
