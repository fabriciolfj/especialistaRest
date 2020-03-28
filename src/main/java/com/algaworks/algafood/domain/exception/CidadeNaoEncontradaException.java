package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -9003882764994684827L;

    public CidadeNaoEncontradaException(String msg){
        super(msg);
    }

    public CidadeNaoEncontradaException(Long id){
        this(String.format("Cidade não encontrada para o id: %d", id));
    }

}
