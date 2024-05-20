package br.com.delflix.domain.service.dvdCatalog.updateDvd;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.delflix.application.utils.GenderUtils.GetGender;
import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.delflix.shared.request.DdvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class UpdateDvdService implements IUpdateDvdService
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final IUpdateOnlyDvdRepository _updateDvdRepository;

    @Autowired
    private final ModelMapper _mapper;

    public UpdateDvdService(IReadOnlyDvdRepository readOnlyRespository, 
    IUpdateOnlyDvdRepository updateDvdRepository, ModelMapper mapper) 
    {
        _readOnlyRespository = readOnlyRespository;
        _updateDvdRepository = updateDvdRepository;
        _mapper = mapper;
    }

    @Override
    public ResponseDvdJson UpdateDvd(RequestDvdJson request, String identifier) 
    {
        Validate(identifier);

        var dvdEntity = UpdateDvdInfo(request, identifier);

        return BuildResponseDvdEntity(dvdEntity);

    }

    private void Validate(String identifier)
    {
        if(!DvdExists(identifier))
        {
            throw new ErrorOnValidationException("Dvd not found");
        }
    }
    
    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private Dvd UpdateDvdInfo(RequestDvdJson requets, String identifier)
    {
        var dvdEntity = BuildDvdEntity(requets, identifier);
        
        SaveChanges(dvdEntity);

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

    private void SaveChanges(Dvd dvdEntity)
    {
        _updateDvdRepository.UpdateDvd(dvdEntity);
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
