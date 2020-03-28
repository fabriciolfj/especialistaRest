package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeRelacionadaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public ResponseEntity<List<CidadeModel>> findAll(){
        return ResponseEntity.ok().body(cadastroCidadeService.findAll());
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
