package com.algaworks.algafood.domain.exception;

public class PermissaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -618651488588207041L;

    public PermissaNaoEncontradaException(String msg) {
        super(msg);
    }

    public PermissaNaoEncontradaException(Long id){
        this(String.format("Permissão não encontrada para o id %d", id));
    }
}
