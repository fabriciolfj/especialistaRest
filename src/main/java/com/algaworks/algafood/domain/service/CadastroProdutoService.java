package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CadastroProdutoService {

    private final ProdutoRepository produtoRepository;


    public Produto findById(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public Produto findById(Long id, Long restaurandId){
        return produtoRepository.findById(restaurandId, id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<Produto> findByRestaurante(Restaurante restaurante){
        return produtoRepository.findByRestaurante(restaurante);
    }

    public List<Produto> findAtivoProdutosRestaurante(Restaurante restaurante){
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Transactional
    public Produto save(Produto produto){
        return produtoRepository.save(produto);
    }
}
