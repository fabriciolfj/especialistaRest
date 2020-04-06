package com.algaworks.algafood.domain.service;


import com.algaworks.algafood.domain.model.Pedido;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.confirmar();

        pedidoRepository.save(pedido); // o evento dispara apenas se chamar um metodo do spring data, mesmo que o findbyid ja salva, pois está dentro de uma transacao
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedido.findByCodigo(codigo);
        pedido.entregar();
    }


}
