package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradaoException(String codigo){
        super(String.format("Pedido não encontrado para o id %s", codigo));
    }

    public PedidoNaoEncontradaoException(Long id) {
        this(String.format("Pedido não encontrado para o id %d", id));
    }
}
