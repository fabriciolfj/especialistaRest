package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {

    private String codigo;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private RestauranteResumo restaurante;
    private UsuarioModel cliente;
    private FormaPagamentoModel formaPagamento;
    private EnderecoModel enderecoEntrega;
    private List<ItemPedidoModel> itens;
}
