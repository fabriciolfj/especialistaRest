package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.CidadeInputDesassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CadastroCidadeService {

    private static final String CIDADE_NAO_ENCONTRADA = "Cidade não encontrada para o id %d";
    private static final String ESTADO_NAO_LOCALIZADO = "Estado não encontrado para o id: ";

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDesassembler cidadeInputDesassembler;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    public List<CidadeModel> findAll(){
        return cidadeModelAssembler.toListModel(cidadeRepository.findAll());
    }

    public CidadeModel findById(Long id){
        return cidadeModelAssembler.toModel(buscarOuFalhar(id));
    }

    public Cidade buscarOuFalhar(Long id){
        return cidadeRepository.findById(id).orElseThrow(() ->
                new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public void delete(Long id){
        try{
            cidadeRepository.deleteById(id);
            cidadeRepository.flush(); //forcar a descarregar os dados na base de dados.
        }catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Cidade encontra-se em uso: s%", id));
        }
    }

    public CidadeModel update(Long id, CidadeInput cidadeInput){
        var cidadeEntity = cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
        cidadeInputDesassembler.copyToDomainObject(cidadeInput, cidadeEntity);
        cidadeEntity.setEstado(cadastroEstadoService.findById(cidadeEntity.getEstado().getId()));
        return cidadeModelAssembler.toModel(cidadeRepository.save(cidadeEntity));
    }

    @Transactional
    public CidadeModel create(CidadeInput cidadeInput){
        try{
            var cidade = cidadeInputDesassembler.toDomainObject(cidadeInput);
            var estado = cadastroEstadoService.findById(cidade.getEstado().getId());
            cidade.setEstado(estado);
            return cidadeModelAssembler.toModel(cidadeRepository.save(cidade));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }

    }
}
