package br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.domain.repository.dvdRepository.IReadOnlyDvdRepository;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;

@Service
public class GetDvdsCatalogUseCase implements IGetDvdsCatalogUseCase
{
    @Autowired
    private IReadOnlyDvdRepository _readOnlyRepository;

    @Autowired
    private ModelMapper _mapper;

    public GetDvdsCatalogUseCase(IReadOnlyDvdRepository readOnlyRepository,
    ModelMapper mapper) 
    {
        _readOnlyRepository = readOnlyRepository;
        _mapper = mapper;
    }

    @Override
    public ResponseDvdsCatalogJson Execute() 
    {
        var dvdListCatalog = BuildResponseDvdsCatalogJson();

        return dvdListCatalog;
    }

    private ResponseDvdsCatalogJson BuildResponseDvdsCatalogJson() 
    {
        var dvdsList = GetDvdList();

        List<ResponseDvdJson> response = dvdsList.stream()
            .map(dvd -> _mapper.map(dvd, ResponseDvdJson.class))
            .collect(Collectors.toList()); 

        return new ResponseDvdsCatalogJson(
            response
        );
    }

    private List<Dvd> GetDvdList()
    {
        return _readOnlyRepository.GetDvdsCatalog();
    }

}
