package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.EstadoInputDesassembler;
import com.algaworks.algafood.api.model.input.EstadoNameInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CadastroEstadoService {

    public static final String ESTADO_EM_USO = "Estado com id %d, encontra-se em uso";
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoInputDesassembler estadoInputDesassembler;

    public List<Estado> findAll(){
        return estadoRepository.findAll();
    }

    public Estado update(Long id, EstadoNameInput estadoNameInput){
        var estadoEntity = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));

        estadoInputDesassembler.copyToDomainObject(estadoNameInput, estadoEntity);
        return estadoRepository.save(estadoEntity);
    }

    @Transactional
    public Estado create(EstadoNameInput estadoNameInput){
        var estado = estadoInputDesassembler.toDomainObject(estadoNameInput);
        return estadoRepository.save(estado);
    }

    public Estado findById(Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public void remover(Long id){
        try{
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, id));
        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(id);
        }
    }
}
