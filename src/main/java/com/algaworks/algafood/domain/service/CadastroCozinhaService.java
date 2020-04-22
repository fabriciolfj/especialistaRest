package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroCozinhaService {

    public static final String COZINHA_EM_USO = "Cozinha de codigo %d, não pode ser excluida pois está em uso";

    private final CozinhaRepository cozinhaRepository;
    private final CozinhaModelAssembler cozinhaModelAssembler;

    @Transactional
    public CozinhaModel salvar(Cozinha cozinha){
        return cozinhaModelAssembler.toModel(cozinhaRepository.save(cozinha));
    }

    @Transactional
    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        }catch(EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(
                COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscarOuFalhar(Long idCozinha){
        return cozinhaRepository.findById(idCozinha)
            .orElseThrow(() -> new CozinhaNaoEncontradaException(idCozinha));
    }
}
