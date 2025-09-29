package com.algaworks.algafood.api.model.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {

    @NotNull
    private RestauranteInputId restaurante;
    @NotNull
    private FormaPagamentoInputId formaPagamento;
    @NotNull
    private EnderecoInput enderecoEntrega;
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;
}
