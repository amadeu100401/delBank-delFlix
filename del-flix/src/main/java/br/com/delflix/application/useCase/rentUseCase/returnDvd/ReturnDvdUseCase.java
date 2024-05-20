package br.com.delflix.application.useCase.rentUseCase.returnDvd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.service.rentDvd.returnDvd.IReturnDvdService;

@Service
public class ReturnDvdUseCase implements IReturnDvdUseCase
{

    @Autowired
    private final IReturnDvdService _returnDvdService;

    public ReturnDvdUseCase(IReturnDvdService returnDvdService) 
    {
        _returnDvdService = returnDvdService;
    }

    @Override
    public void Execute(String identifier) 
    {
        _returnDvdService.ReturnDvd(identifier);
    }
}
