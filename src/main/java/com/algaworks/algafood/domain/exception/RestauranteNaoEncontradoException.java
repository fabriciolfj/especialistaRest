package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -9003882764994684827L;

    public RestauranteNaoEncontradoException(String msg){
        super(msg);
    }

    public RestauranteNaoEncontradoException(Long id){
        this(String.format("Restaurante não encontrado para o id: %d", id));
    }

}
