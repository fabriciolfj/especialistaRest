package com.algaworks.algafood.teste.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.teste.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {
	
	//@Bean(initMethod = "init", destroyMethod = "destroy")
	@Bean
	public AtivacaoClienteService ativacaoClienteService() {
		return new AtivacaoClienteService();
	}

}
