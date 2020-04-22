package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public CollectionModel<CidadeModel> findAll(){
        return cadastroCidadeService.findAll();
    }

    @GetMapping("/{id}")
    public CidadeModel findById(@PathVariable("id")Long id){
        return cadastroCidadeService.findById(id);
    }

    @PutMapping("/{id}")
    public CidadeModel update(@Valid @RequestBody CidadeInput cidadeInput, @PathVariable("id") Long id){
        return cadastroCidadeService.update(id, cidadeInput);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel create(@Valid @RequestBody CidadeInput cidadeInput){
        return cadastroCidadeService.create(cidadeInput);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        cadastroCidadeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
