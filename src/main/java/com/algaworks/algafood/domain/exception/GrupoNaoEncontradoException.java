package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -356472756742455090L;

    public GrupoNaoEncontradoException(String msg) {
        super(msg);
    }

    public GrupoNaoEncontradoException(Long id){
        this(String.format("Grupo não encontrado para o id %d", id));
    }
}
