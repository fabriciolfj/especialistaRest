package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        var cadastroCozinha = context.getBean(CozinhaRepository.class);

        var cozinhas = cadastroCozinha.findAll();
        cozinhas.stream().forEach(p ->
            System.out.println(p.getNome())
        );


    }
}
