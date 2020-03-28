package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDesassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoNameInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){
        return cadastroEstadoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel create(@Valid @RequestBody EstadoNameInput estadoNameInput){
        var estado = cadastroEstadoService.create(estadoNameInput);
        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoModel> atualizar(@Valid @RequestBody EstadoNameInput estado, @PathVariable("id") Long id){
        var obj = cadastroEstadoService.update(id, estado);
        return ResponseEntity.ok().body(estadoModelAssembler.toModel(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> findById(@PathVariable("id") Long id){
        var estado = cadastroEstadoService.findById(id);
        return ResponseEntity.ok().body(estadoModelAssembler.toModel(estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        cadastroEstadoService.remover(id);
        return ResponseEntity.ok().build();
    }
}

