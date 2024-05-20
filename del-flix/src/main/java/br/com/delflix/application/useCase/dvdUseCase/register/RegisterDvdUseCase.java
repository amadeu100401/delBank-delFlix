package br.com.delflix.application.useCase.dvdUseCase.register;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.application.validator.dvdValidation.IValidateDvdRequest;
import br.com.delflix.domain.service.dvdCatalog.registerDvd.IRegisterDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.DdvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class RegisterDvdUseCase implements IRegisterDvdUseCase
{
    @Autowired
    private final IValidateDvdRequest _validateRequest;
    @Autowired
    private final IRegisterDvdService _registerDvdService;

    public RegisterDvdUseCase(IValidateDvdRequest validateRequest, 
    IRegisterDvdService registerDvdService)
    {
        _validateRequest = validateRequest;
        _registerDvdService = registerDvdService;
    }

   @Override
    public ResponseDvdJson Execute(RequestDvdJson request) 
    {
        Validate(request);

        var response = _registerDvdService.RegisterDvd(request);

        return response;
    }

    private void Validate(RequestDvdJson request)
    {
        List<String> errorsMessager = new ArrayList<>();

        var resultDvdRequest = _validateRequest.ValidateDvdRequest(request);
        var resultAuthorRequest = _validateRequest.ValidateRequestAuthor(request.getAuthor());

        if(!_validateRequest.IsValidRequest(resultDvdRequest))
        {
            _validateRequest.GetErrorMessage(errorsMessager,resultDvdRequest);
        }
        if(!_validateRequest.IsValidRequest(resultAuthorRequest))
        {
            _validateRequest.GetErrorMessage(errorsMessager,resultAuthorRequest);
        }
        if(!errorsMessager.isEmpty())
        {
            throw new ErrorOnValidationException(errorsMessager);
        }
    }
}
