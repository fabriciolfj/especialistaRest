package com.algaworks.algafood.teste.notificacao;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.teste.modelo.Cliente;

@Component
@TipoDoNotificador(NivelUrgencia.URGENTE)
public class NotificadorSms implements Notificador {
	
	/*private boolean caixaAlta;
	private String hostServidorSmtp;
	
	public NotificadorEmail(String hostServidorSmtp) {
		this.hostServidorSmtp = hostServidorSmtp;
	}*/
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		/*if(caixaAlta) {
			mensagem = mensagem.toUpperCase();
		}*/
		
		System.out.printf("Notificando %s por SMS pelo telefone  %s: %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);
	}
	
	/*public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}*/
}
