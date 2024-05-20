package br.com.delflix.domain.service.rentDvd.getRentDvd;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.domain.repository.dvdRepository.IUpdateOnlyDvdRepository;
import br.com.delflix.shared.exception.ErrorOnValidationException;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;

@Service
public class GetRentDvdService implements IGetRentDvdService
{
    @Autowired
    private final IUpdateOnlyDvdRepository _updateOnlyRespository;

    @Autowired
    private final IReadOnlyDvdRepository _readOnlyRespository;

    @Autowired
    private final ModelMapper _mapper;

    public GetRentDvdService(IUpdateOnlyDvdRepository updateOnlyRespository,
    IReadOnlyDvdRepository readOnlyRespository, ModelMapper mapper) 
    {
        _updateOnlyRespository = updateOnlyRespository;
        _readOnlyRespository = readOnlyRespository;
        _mapper = mapper;
    }

    @Override
    public ResponseRentDvdJson GetRentDvd(RequestRentJson request) 
    {
        ValidateDvd(request.getDvdIdentifier());
        return RentDvd(request);
    }
    
    private void ValidateDvd(String dvdIdentifier)
    {
        if(!DvdExists(dvdIdentifier))
        {
            throw new ErrorOnValidationException("Dvd not found");
        }

        if(!IsDvdAvailable(dvdIdentifier))
        {
            throw new ErrorOnValidationException("Dvd not available");
        }
    }

    private boolean DvdExists(String dvdIdentifier)
    {
        return _readOnlyRespository.DvdExistsByIdentifier(dvdIdentifier);
    }

    private boolean IsDvdAvailable(String dvdIdentifier)
    {
        var dvd = GetDvdInDb(dvdIdentifier);

        if(dvd == null ||dvd.getCopiesQuantity() == 0 || !dvd.isAviable())
        {
            return false;
        }

        return true;
    }

    private Dvd GetDvdInDb(String identifier)
    {
        return _readOnlyRespository.GetDvdByIdentifier(identifier);
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
}
