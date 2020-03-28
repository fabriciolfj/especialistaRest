package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cozinhas" )//, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Cozinha> listar(@PageableDefault(size = 10) Pageable pageable){
        return cozinhaRepository.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@Valid  @RequestBody Cozinha cozinha, @PathVariable("id") Long id){
        var cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        cadastroCozinha.salvar(cozinhaAtual);
        return ResponseEntity.ok(cozinhaAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCozinha.excluir(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@Valid @RequestBody Cozinha cozinha){
        return cadastroCozinha.salvar(cozinha);
    }

    @GetMapping("/{id}")
    //@ResponseStatus(HttpStatus.OK)
    public Cozinha findById(@PathVariable("id")  Long id){
        return cadastroCozinha.buscarOuFalhar(id);

        /*if(cozinha.isPresent()){
            return ResponseEntity.ok(cozinha.get());
        }

        return ResponseEntity.notFound().build();*/

        /*HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
        //return ResponseEntity.ok(cozinha);
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();*/
    }
}
