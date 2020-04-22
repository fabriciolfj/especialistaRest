package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.model.input.GrupoNomeInput;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final CadastroGrupoService grupoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel create(@Valid @RequestBody GrupoNomeInput input){
        return grupoService.save(input);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoModel update(@Valid @RequestBody GrupoNomeInput input, @PathVariable Long grupoId){
        return grupoService.update(input, grupoId);
    }

    @GetMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoModel findByID(@PathVariable Long grupoId){
        return grupoService.findById(grupoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GrupoModel> findAll(){
        return grupoService.findAll();
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long grupoId){
        grupoService.deleteById(grupoId);
    }

    @GetMapping("/{grupoId}/permissoes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<PermissaoModel> getPermissoes(@PathVariable Long grupoId){
        return grupoService.getPermissoes(grupoId);
    }

    @PutMapping("/{grupoId}/permissoes/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.adicionarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{grupoId}/permissoes/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.removerPermissao(grupoId, permissaoId);
    }

}
