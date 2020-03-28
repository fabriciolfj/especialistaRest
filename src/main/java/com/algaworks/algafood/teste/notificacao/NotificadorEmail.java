package com.algaworks.algafood.teste.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.teste.modelo.Cliente;

//@Primary // esse bean será o principal das implementações de notificador
@Component
//@Profile("production")
@Qualifier("SEM_URGENCIA")
//@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
public class NotificadorEmail implements Notificador {
	
	/*private boolean caixaAlta;
	private String hostServidorSmtp;
	
	public NotificadorEmail(String hostServidorSmtp) {
		this.hostServidorSmtp = hostServidorSmtp;
	}*/

	@Autowired
	private NotificadorProperties properties;
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		/*if(caixaAlta) {
			mensagem = mensagem.toUpperCase();
		}*/

		System.out.println(properties.getHostServidor());
		System.out.println(properties.getPortaServidor());
		
		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}
	
	/*public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}*/
}
