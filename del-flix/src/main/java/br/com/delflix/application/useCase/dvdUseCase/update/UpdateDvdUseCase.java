package br.com.delflix.application.useCase.dvdUseCase.update;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.application.validator.dvdValidation.IValidateDvdRequest;
import br.com.delflix.domain.service.dvdCatalog.updateDvd.IUpdateDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.DdvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class UpdateDvdUseCase implements IUpdateDvdUseCase
{    
    @Autowired
    private final IValidateDvdRequest _validateRequest;
    @Autowired
    private final IUpdateDvdService _updateDvdService;

    public UpdateDvdUseCase(IValidateDvdRequest validateRequest,
    IUpdateDvdService updateDvdService) {
        _validateRequest = validateRequest;
        _updateDvdService = updateDvdService;
    }

    @Override
    public ResponseDvdJson Execute(RequestDvdJson request, String identifier) 
    {
        Validate(request, identifier);

        var response = _updateDvdService.UpdateDvd(request, identifier);

        return response;
    }

    private void Validate(RequestDvdJson request, String guid)
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
