package br.com.delflix.application.useCase.rentUseCase.getRent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.application.validator.rentValidation.IValidateRentRequest;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.service.rentDvd.getRentDvd.IGetRentDvdService;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

@Service
public class RentDvdUseCase implements IRentDvdUseCase
{
    @Autowired
    private final IValidateRentRequest _validateRequest;

    @Autowired
    private final IGetRentDvdService _getRentDvdService;

    public RentDvdUseCase(IValidateRentRequest validateRequest,
    IReadOnlyDvdRepository readOnlyRespository,
    IGetRentDvdService getRentDvdService) 
    {
        _validateRequest = validateRequest;
        _getRentDvdService = getRentDvdService;
    }

    @Override
    public ResponseRentDvdJson Execute(RequestRentJson request) 
    {
        Validate(request);
        var response = _getRentDvdService.GetRentDvd(request);
        return response;
    }

    private void Validate(RequestRentJson request)
    {
        List<String> errorsMessager = new ArrayList<>();

        var resultDvdRequest = _validateRequest.ValidateRequest(request);

        if(!_validateRequest.IsValidRequest(resultDvdRequest))
        {
            _validateRequest.GetErrorMessage(errorsMessager,resultDvdRequest);
            throw new ErrorOnValidationException(errorsMessager);
        }
    }

}
