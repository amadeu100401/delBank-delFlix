package br.com.delflix.application.useCase.rentUseCase.rentDvd;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.application.validator.rentValidation.IValidateRentRequest;
import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

@Service
public class RentDvdUseCase implements IRentDvdUseCase
{
    @Autowired
    private final IValidateRentRequest _validateRequest;

    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final ModelMapper _mapper;

    public RentDvdUseCase(IValidateRentRequest validateRequest,
    IReadOnlyDvdRepository readOnlyRespository,
    IUpdateOnlyDvdRepository updateOnlyRespository,
    ModelMapper mapper) 
    {
        _validateRequest = validateRequest;
        _readOnlyRespository = readOnlyRespository;
        _updateOnlyRespository = updateOnlyRespository;
        _mapper = mapper;
    }

    @Override
    public ResponseRentDvdJson Execute(RequestRentJson request) 
    {
        Validate(request);
        var response = RentDvd(request);
        return response;
    }

    private void Validate(RequestRentJson request)
    {
        List<String> errorsMessager = new ArrayList<>();

        var resultDvdRequest = _validateRequest.ValidateRequest(request);

        if(!_validateRequest.IsValidRequest(resultDvdRequest))
        {
            _validateRequest.GetErrorMessage(errorsMessager,resultDvdRequest);
        }

        ValidateDvd(request.getDvdIdentifier(), errorsMessager);

        if(!errorsMessager.isEmpty())
        {
            throw new ErrorOnValidationException(errorsMessager);
        }
    }

    private List<String> ValidateDvd(String dvdIdentifier, List<String> errorsMessager)
    {
        if(!DvdExists(dvdIdentifier))
        {
            errorsMessager.add("Dvd not found");
        }

        if(!IsDvdAvailable(dvdIdentifier))
        {
            errorsMessager.add("Dvd not available");
        }

        return errorsMessager;
    }

    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private boolean IsDvdAvailable(String dvdIdentifier)
    {
        var dvd = _readOnlyRespository.GetDvdByIdentifier(dvdIdentifier);

        if(dvd.getCopiesQuantity() == 0 || !dvd.isAviable())
        {
            return false;
        }

        return true;
    }

    private ResponseRentDvdJson RentDvd(RequestRentJson request)
    {
        var rent = _mapper.map(request, ResponseRentDvdJson.class);

        var dvd = GetDvdInDb(request.getDvdIdentifier());

        rent.setTitle(dvd.getTitle());

        dvd.setCopiesQuantity(dvd.getCopiesQuantity() - 1);
        
        if(dvd.getCopiesQuantity() == 0)
        {
            dvd.setAviable(false);
        }

        SaveChanges(dvd);

        return rent;
    }

    private void SaveChanges(Dvd data)
    {
        _updateOnlyRespository.UpdateDvd(data);
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _readOnlyRespository.GetDvdByIdentifier(identifier);
    }
}
