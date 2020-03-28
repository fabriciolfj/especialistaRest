package com.algaworks.algafood.teste.notificacao;

import com.algaworks.algafood.teste.modelo.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);

}