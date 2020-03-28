package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoModel {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;
}
