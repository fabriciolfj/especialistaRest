package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal precoFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    private Boolean ativo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnderecoModel endereco;
    private Boolean aberto;
}
