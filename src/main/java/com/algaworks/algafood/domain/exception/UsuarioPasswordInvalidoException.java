package com.algaworks.algafood.domain.exception;

public class UsuarioPasswordInvalidoException extends NegocioException {

    private static final long serialVersionUID = 8090771037158517728L;

    public UsuarioPasswordInvalidoException(String msg) {
        super(msg);
    }
}
