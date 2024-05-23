package br.com.delflix.application.useCase.dvdUseCase.activate;

import br.com.delflix.domain.service.dvdCatalog.activateDvd.IActivateDvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivateDvdUseCase implements IActivateDvdUseCase
{
    @Autowired
    private final IActivateDvdService _activateDvdService;

    public ActivateDvdUseCase(IActivateDvdService activateDvdService)
    {
        _activateDvdService = activateDvdService;
    }

    @Override
    public void Execute(String identifier)
    {
        _activateDvdService.Execute(identifier);
    }
}
