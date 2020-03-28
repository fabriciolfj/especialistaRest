package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = -9003882764994684827L;

    public NegocioException(String msg){
        super(msg);
    }
}
