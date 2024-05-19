package br.com.delflix.application.useCase.dvdUseCase.update;

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
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class UpdateDvdUseCase implements IUpdateDvdUseCase
{    
    @Autowired
    private final IValidateDvdRequest _validateRequest;
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;
    @Autowired
    private final IUpdateOnlyDvdRepository _updateDvdRepository;
    @Autowired
    private final ModelMapper _mapper;

    public UpdateDvdUseCase(IValidateDvdRequest validateRequest,
    IReadOnlyDvdRepository readOnlyRespository, 
    IUpdateOnlyDvdRepository updateDvdRepository,
    ModelMapper mapper) {
        _validateRequest = validateRequest;
        _readOnlyRespository = readOnlyRespository;
        _updateDvdRepository = updateDvdRepository;
        _mapper = mapper;
    }

    @Override
    public ResponseDvdJson Execute(RequestDvdJson request, String identifier) 
    {
        Validate(request, identifier);

        var dvdEntity = UpdateDvd(request, identifier);

        var response = BuildResponseDvdEntity(dvdEntity);

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
        if(!DvdExists(guid))
        {
            errorsMessager.add("Dvd not found");
        }
        if(!errorsMessager.isEmpty())
        {
            throw new ErrorOnValidationException(errorsMessager);
        }
    }

    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private Dvd UpdateDvd(RequestDvdJson requets, String identifier)
    {
        var dvdEntity = BuildDvdEntity(requets, identifier);
        _updateDvdRepository.UpdateDvd(dvdEntity);

        return dvdEntity;
    }

    private Dvd BuildDvdEntity(RequestDvdJson request, String identifier)
    {
        var dvdEntity = _updateDvdRepository.GetDvdInDb(identifier);

        dvdEntity.setTitle(request.getTitle());
        dvdEntity.setAviable(request.isAviable());
        dvdEntity.setAuthor(BuildAuthorEntity(request.getAuthor()));
        dvdEntity.setUpdateAt(new Date());
        dvdEntity.setGender(GetGender(request));

        return dvdEntity;
    }

    private br.com.delflix.domain.entity.Author BuildAuthorEntity(RequestAuthorRegisterJson request)
    {
        return _mapper.map(request, br.com.delflix.domain.entity.Author.class);
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
