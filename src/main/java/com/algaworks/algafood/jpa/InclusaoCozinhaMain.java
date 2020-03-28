package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        var cadastroCozinha = context.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japoneza");

        cozinha = cadastroCozinha.save(cozinha);
        cozinha2 = cadastroCozinha.save(cozinha2);

        System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
        System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
    }
}
