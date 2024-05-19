package br.com.delflix.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.delflix.application.useCase.dvdUseCase.delete.fisical.IDeleteDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.delete.logical.ILogicalDeleteUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.GetDvdsCatalog.IGetDvdsCatalogUseCase;
import br.com.delflix.application.useCase.dvdUseCase.get.getDvd.IGetDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.register.IRegisterDvdUseCase;
import br.com.delflix.application.useCase.dvdUseCase.update.IUpdateDvdUseCase;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;



@RestController
@RequestMapping("/api/dvd/v1")
public class DvdController 
{
    @Autowired
    private final IRegisterDvdUseCase _registerDvdUseCase;

    @Autowired
    private final IUpdateDvdUseCase _updateDvdUseCase;

    @Autowired
    private final ILogicalDeleteUseCase _logicalDeleteUseCase;

    @Autowired
    private final IDeleteDvdUseCase _deleteUseCase;

    @Autowired
    private final IGetDvdsCatalogUseCase _dvdsCatalogUseCase;

    @Autowired
    private final IGetDvdUseCase _getDvdUseCase;

    public DvdController(IRegisterDvdUseCase registerDvdUseCase, 
    IUpdateDvdUseCase updateDvdUseCase, 
    ILogicalDeleteUseCase logicalDeleteUseCase,
    IDeleteDvdUseCase deleteUseCase,
    IGetDvdsCatalogUseCase dvdsCatalogUseCase,
    IGetDvdUseCase getDvdUseCase) 
    {
        _registerDvdUseCase = registerDvdUseCase;
        _updateDvdUseCase = updateDvdUseCase;
        _logicalDeleteUseCase = logicalDeleteUseCase;
        _deleteUseCase = deleteUseCase;
        _dvdsCatalogUseCase = dvdsCatalogUseCase;
        _getDvdUseCase = getDvdUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDvdJson> Register(@RequestBody RequestDvdJson request) 
    {
        var response = _registerDvdUseCase.Execute(request);

        return ResponseEntity.created(null).body(response);
    }

    @GetMapping("/dvd")
    public ResponseEntity<ResponseDvdsCatalogJson> GetDvdsCatalog() 
    {
        var response = _dvdsCatalogUseCase.Execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dvd/{identifier}")
    public ResponseEntity<ResponseDvdJson> getMethodName(@PathVariable String identifier) 
    {
        var response = _getDvdUseCase.Execute(identifier);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/update/{identifier}")
    public ResponseEntity<ResponseDvdJson> UpdateDvd(@PathVariable String identifier, @RequestBody RequestDvdJson request) 
    {   
        var response = _updateDvdUseCase.Execute(request, identifier);
        return ResponseEntity.ok(response);
    }

    @PutMapping("delete/{identifier}")
    public ResponseEntity PutMethodName(@PathVariable String identifier) 
    {    
        _logicalDeleteUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{identifier}")
    public ResponseEntity DeleteMethodName(@PathVariable String identifier) 
    {    
        _deleteUseCase.Execute(identifier);
        return ResponseEntity.noContent().build();
    }
}
