package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BonificacaoClientePedidoConfirmado {

    @EventListener
    public void aoConfirmarPedido(final PedidoConfirmadoEvent event) {
        var pedido = event.getPedido();
        System.out.println("Calculando pontos para o cliente: " + pedido.getCliente().getNome());
    }
}
