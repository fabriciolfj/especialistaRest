package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -2217370581419074740L;

    public FormaPagamentoNaoEncontradaException(String msg) {
        super(msg);
    }

    public FormaPagamentoNaoEncontradaException(Long id){
        this(String.format("Forma de pagamento não encontrada para o id %d", id));
    }

    public FormaPagamentoNaoEncontradaException(Long id, Long restauranteId){
        this(String.format("Forma de pagamento com %d, não encontrada para o restaurante %d", id, restauranteId));
    }
}
