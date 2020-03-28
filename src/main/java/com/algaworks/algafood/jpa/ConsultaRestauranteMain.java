package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        var restaurante = context.getBean(RestauranteRepository.class);

        var restaurantes = restaurante.findAll();
        restaurantes.stream().forEach(p ->
            System.out.printf("%s - %f - %s\n", p.getNome(), p.getTaxaFrete(), p.getCozinha().getNome())
        );
    }
}
