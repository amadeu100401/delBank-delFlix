package br.com.delflix.application.useCase.dvdUseCase.delete.logical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.service.dvdCatalog.deleteDvd.logicalDelete.ILogicalDeleteDvdService;

@Service
public class LogicalDeleteUseCase implements ILogicalDeleteUseCase
{
    @Autowired
    private final ILogicalDeleteDvdService _deleteDvdService;

    public LogicalDeleteUseCase(ILogicalDeleteDvdService deleteDvdService) 
     {
        _deleteDvdService = deleteDvdService;
    }

    @Override
    public void Execute(String identifier) 
    {
        _deleteDvdService.DeleteDvd(identifier);
    }
}
