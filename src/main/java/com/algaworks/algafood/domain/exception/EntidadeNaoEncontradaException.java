package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = -9003882764994684827L;

    public EntidadeNaoEncontradaException(String msg){
        super(msg);
    }
}
