package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoConfirmado {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) //vai disparar dp que a transacao for commitada, se der problema no envio do email ele da rollback,
    // caso queira que commita mesmo que de algum problema no envio do email uso o after
    public void aoConfirmarPedido(final PedidoConfirmadoEvent event) {
        var pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
