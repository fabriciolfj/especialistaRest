package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioInputDesassembler;
import com.algaworks.algafood.api.assembler.UsuarioNameEmailInputDesassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioNameEmailInput;
import com.algaworks.algafood.api.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CadastroUsuarioService cadastroUsuarioService;
    private final UsuarioInputDesassembler usuarioInputDesassembler;
    private final UsuarioNameEmailInputDesassembler usuarioNameEmailInputDesassembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel create(@Valid @RequestBody UsuarioInput input){
        var usuario = usuarioInputDesassembler.toObject(input);
        return cadastroUsuarioService.save(usuario);
    }

    @GetMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsuarioModel findById(@PathVariable Long usuarioId){
        return cadastroUsuarioService.findById(usuarioId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioModel> findAll(){
        return cadastroUsuarioService.findAll();
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsuarioModel update(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioNameEmailInput input){
        var usuario = usuarioNameEmailInputDesassembler.toObject(input);
        return cadastroUsuarioService.update(usuario, usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSenha(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioPasswordInput input){
        cadastroUsuarioService.updatePassword(input, usuarioId);
    }

    @GetMapping("/{usuarioId}/grupos")
    @ResponseStatus(HttpStatus.OK)
    public List<GrupoModel> getGrupos(@PathVariable Long usuarioId){
        return cadastroUsuarioService.getGrupos(usuarioId);
    }

    @GetMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoModel getGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        return cadastroUsuarioService.getGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuarioService.adicionarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuarioService.removerGrupo(usuarioId, grupoId);
    }


}
