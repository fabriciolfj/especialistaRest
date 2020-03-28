package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {


    private static final long serialVersionUID = 1655195048854609076L;

    public UsuarioNaoEncontradoException(String msg){
        super(msg);
    }

    public UsuarioNaoEncontradoException(Long id){
        this(String.format("Usuario não encontrado para o id: %d", id));
    }

}
