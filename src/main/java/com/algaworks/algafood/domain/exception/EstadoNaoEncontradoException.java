package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -9003882764994684827L;

    public EstadoNaoEncontradoException(String msg){
        super(msg);
    }

    public EstadoNaoEncontradoException(Long id){
        this(String.format("Estado não encontrado para o id: %d", id));
    }

}
