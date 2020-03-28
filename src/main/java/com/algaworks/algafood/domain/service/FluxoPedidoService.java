package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import static java.util.Optional.of;


@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.entregar();
    }


}
