package com.algaworks.algafood.teste.listener;

import com.algaworks.algafood.teste.service.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscalService {

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event){
        System.out.println("Emitindo a nota fiscal para o cliente: " + event.getCliente().getNome());
    }
}
