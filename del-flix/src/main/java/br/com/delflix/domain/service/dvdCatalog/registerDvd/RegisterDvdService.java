package br.com.delflix.domain.service.dvdCatalog.registerDvd;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.delflix.application.utils.GenderUtils.GetGender;
import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IWriteOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;

@Service
public class RegisterDvdService implements IRegisterDvdService {

    @Autowired
    private final IWriteOnlyDvdRepository _writeOnlyRespository;

    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final ModelMapper _mapper;

    public RegisterDvdService(IWriteOnlyDvdRepository writeOnlyRespository,
            IReadOnlyDvdRepository readOnlyRespository, ModelMapper mapper) {
        _writeOnlyRespository = writeOnlyRespository;
        _readOnlyRespository = readOnlyRespository;
        _mapper = mapper;
    }

    @Override
    public ResponseDvdJson RegisterDvd(RequestDvdJson request) {
        Validate(request.getTitle());
        var dvdEntity = Save(request);

        return BuildResponseDvdEntity(dvdEntity);
    }

    private void Validate(String title) {
        if (DvdAlreadyRegistred(title)) {
            throw new ErrorOnValidationException("Dvd already registred");
        }
    }

    private boolean DvdAlreadyRegistred(String title) {
        return _readOnlyRespository.DvdAlreadyRegistred(title);
    }

    private Dvd Save(RequestDvdJson request) {
        var dvdEntity = BuildDvdEntity(request);

        _writeOnlyRespository.SaveDvd(dvdEntity);

        return dvdEntity;
    }

    private Dvd BuildDvdEntity(RequestDvdJson request) {
        var dvdEntity = _mapper.map(request, Dvd.class);
        dvdEntity.setUpdateAt(new Date());
        dvdEntity.setGender(GetGender(request));

        return dvdEntity;
    }

    private ResponseDvdJson BuildResponseDvdEntity(Dvd dvdEntity) {
        return new ResponseDvdJson(
                dvdEntity.getTitle(),
                dvdEntity.getCopiesQuantity(),
                dvdEntity.getIdentifier()
        );
    }
}
