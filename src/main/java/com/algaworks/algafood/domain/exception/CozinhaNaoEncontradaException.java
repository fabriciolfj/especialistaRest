package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -9003882764994684827L;

    public CozinhaNaoEncontradaException(String msg){
        super(msg);
    }

    public CozinhaNaoEncontradaException(Long id){
        this(String.format("Cozinha não encontrada para o id: %d", id));
    }

}
