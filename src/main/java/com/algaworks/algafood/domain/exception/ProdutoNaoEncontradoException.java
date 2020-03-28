package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -9003882764994684827L;

    public ProdutoNaoEncontradoException(String msg){
        super(msg);
    }

    public ProdutoNaoEncontradoException(Long id, Long resturanteId){
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de codigo %d", id, resturanteId));
    }

    public ProdutoNaoEncontradoException(Long id){
        this(String.format("Não existe um cadastro de produto com código %d", id));
    }

}
