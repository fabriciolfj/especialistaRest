package com.algaworks.algafood.teste.listener;

import com.algaworks.algafood.teste.notificacao.NivelUrgencia;
import com.algaworks.algafood.teste.notificacao.Notificador;
import com.algaworks.algafood.teste.notificacao.TipoDoNotificador;
import com.algaworks.algafood.teste.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @Autowired
    //@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Qualifier("SEM_URGENCIA")
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event){
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo");
    }
}
