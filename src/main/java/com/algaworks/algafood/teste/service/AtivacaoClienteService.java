package com.algaworks.algafood.teste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.algaworks.algafood.teste.modelo.Cliente;

//@Component
public class AtivacaoClienteService {
	
	/*@Autowired(required = false) injecao de varios notificadores
	private List<Notificador> notificadores;*/
	
	/*@Autowired
	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	private Notificador notificador;*/



	//@PostConstruct
	public void init(){
		System.out.println("INIT");
	}


	//@PreDestroy
	public void destroy(){
		System.out.println("DESTROY");
	}
	
	/*@Autowired
	public AtivacaoClienteService(Notificador notificador) {
		this.notificador = notificador;
	}*/

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public void ativar(Cliente cliente) {
		cliente.ativar();
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
		/*if(notificadores != null){
			notificadores.forEach(notificador -> notificador.notificar(cliente, "Seu cadastro no sistema está ativo"));
			return;
		}
		
		System.out.println("Não existe notificador, mas o cliente foi ativado");*/
	}
	
	/*@Autowired
	public void setNotificador(Notificador notificador) {
		this.notificador = notificador;
	}*/

}
