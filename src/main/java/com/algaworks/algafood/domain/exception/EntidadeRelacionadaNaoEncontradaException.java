package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeRelacionadaNaoEncontradaException extends RuntimeException {

    public EntidadeRelacionadaNaoEncontradaException(String msg){
        super(msg);
    }
}
