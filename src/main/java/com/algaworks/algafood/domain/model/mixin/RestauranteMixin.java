package com.algaworks.algafood.domain.model.mixin;


import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)// permitir metodos get (de objeto para json, no caso do set seria json para objeto)
    private Cozinha cozinha;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;

    //@JsonIgnore
    private OffsetDateTime dataCadastro;

    //@JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
}
