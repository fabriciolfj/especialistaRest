package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;
    @NotNull
    @PositiveOrZero
    private Long quantidade;
    private String observacao;
}
