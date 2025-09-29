package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

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
