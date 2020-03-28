package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonFilter("pedidoFilter")
public class PedidoResumoModel {

    private String codigo;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumo restaurante;
    private String nomeCliente;
    //private UsuarioModel cliente;
}
