package br.com.delflix.domain.service.dvdCatalog.getDvd.getOneDvd;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class GetOneDvdService implements IGetOneDvdService
{
    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final ModelMapper _mapper;

    public GetOneDvdService(IReadOnlyDvdRepository readOnlyRespository,
    ModelMapper mapper) 
    {
        _readOnlyRespository = readOnlyRespository;
        _mapper = mapper;
    }
    @Override
    public ResponseDvdJson GetOneDvd(String identifier) 
    {
        Validate(identifier);
        return BuildResponseDvdEntity(identifier);
    }

        private void Validate(String identifier)
    {
        if (identifier.isBlank())
        {
            throw new ErrorOnValidationException("Identifier can't be null or empty");
        }

        if(!DvdExists(identifier))
        {
            throw new ErrorOnValidationException("Dvd not found");
        }
    }

    private boolean DvdExists(String identifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(identifier);
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _readOnlyRespository.GetDvdByIdentifier(identifier);
    }

    private ResponseDvdJson BuildResponseDvdEntity(String identifier)
    {
        var dvdEntity = GetDvdInDb(identifier);
        return _mapper.map(dvdEntity, ResponseDvdJson.class);
    }
}
