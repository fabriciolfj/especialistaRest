package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.GrupoInputDesassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.model.input.GrupoNomeInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PermissaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroGrupoService {

    private final GrupoRepository grupoRepository;
    private final GrupoInputDesassembler grupoInputDesassembler;
    private final GrupoModelAssembler grupoModelAssembler;
    private final PermissaoModelAssembler permissaoModelAssembler;
    private final PermissaoRepository permissaoRepository;
    private static final String GRUPO_EM_USO = "Grupo em uso %d";

    @Transactional
    public GrupoModel save(GrupoNomeInput input){
        var grupo = grupoInputDesassembler.toObject(input);
        grupo = grupoRepository.save(grupo);
        grupoRepository.flush();
        return grupoModelAssembler.toModel(grupo);
    }

    @Transactional
    public void adicionarPermissao(Long grupoId, Long permissaoId){
        var grupo = getGrupo(grupoId);
        var permissao = getPermissao(permissaoId);
        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void removerPermissao(Long grupoId, Long permissaoId){
        var grupo = getGrupo(grupoId);
        var permissao = getPermissao(permissaoId);
        grupo.removerPermissao(permissao);
    }

    private Permissao getPermissao(Long permissaoId) {
        return permissaoRepository.findById(permissaoId).orElseThrow(() -> new PermissaNaoEncontradaException(permissaoId));
    }

    public List<PermissaoModel> getPermissoes(Long grupoId){
        return getGrupo(grupoId).getPermissoes().stream().map(p -> permissaoModelAssembler.toModel(p)).collect(Collectors.toList());
    }

    public GrupoModel findById(Long id){
        return grupoModelAssembler.toModel(getGrupo(id));
    }

    public Grupo getGrupo(Long id) {
        return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    public List<GrupoModel> findAll(){
        return grupoModelAssembler.toListModel(grupoRepository.findAll());
    }

    @Transactional
    public void deleteById(Long id){
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new GrupoNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(GRUPO_EM_USO, id));
        }
    }

    @Transactional
    public GrupoModel update(GrupoNomeInput input, Long id){
        var grupo = getGrupo(id);
        grupoInputDesassembler.copyToObject(input, grupo);
        grupo = grupoRepository.save(grupo);
        grupoRepository.flush();
        return grupoModelAssembler.toModel(grupo);
    }
}
