package br.com.delflix.application.useCase.dvdUseCase.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.delflix.application.utils.GenderUtils.GetGender;
import br.com.delflix.application.validator.IValidateDvdRequest;
import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IWriteOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class RegisterDvdUseCase implements IRegisterDvdUseCase
{
    @Autowired
    private final IValidateDvdRequest _validateRequest;
    @Autowired
    private final IWriteOnlyDvdRepository _writeOnlyRespository;
    @Autowired
    private final ModelMapper _mapper;
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    public RegisterDvdUseCase(IValidateDvdRequest validateRequest, 
    IWriteOnlyDvdRepository writeOnlyRespository, ModelMapper mapper,
    IReadOnlyDvdRepository readOnlyRespository)
    {
        _validateRequest = validateRequest;
        _writeOnlyRespository = writeOnlyRespository;
        _mapper = mapper;
        _readOnlyRespository = readOnlyRespository;
    }

   @Override
    public ResponseDvdJson Execute(RequestDvdJson request) 
    {
        Validate(request);

        var dvdEntity = Save(request);

        var response = BuildResponseDvdEntity(dvdEntity);

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
        if(DvdAlreadyRegistred(request))
        {
            errorsMessager.add("Dvd already registred");
        }
        if(!errorsMessager.isEmpty())
        {
            throw new ErrorOnValidationException(errorsMessager);
        }
    }

    private boolean DvdAlreadyRegistred(RequestDvdJson request)
    {
        return _readOnlyRespository.DvdAlreadyRegistred(request.getTitle());
    }

    private Dvd Save(RequestDvdJson request)
    {
        var dvdEntity = BuildDvdEntity(request);

        _writeOnlyRespository.SaveDvd(dvdEntity);

        return dvdEntity;
    }

    private Dvd BuildDvdEntity(RequestDvdJson request)
    {
        var dvdEntity = _mapper.map(request, Dvd.class);
        dvdEntity.setUpdateAt(new Date());
        dvdEntity.setGender(GetGender(request));   

        return dvdEntity;
    } 

    private ResponseDvdJson BuildResponseDvdEntity(Dvd dvdEntity)
    {
        return new ResponseDvdJson(
            dvdEntity.getTitle(),
            dvdEntity.getCopiesQuantity(),
            dvdEntity.getIdentifier()
        );
    }
}
