package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        var repository = context.getBean(RestauranteRepository.class);

        var restaurante = new Restaurante();
        restaurante.setNome("Sabor da Casa");
        restaurante.setTaxaFrete(new BigDecimal(0.99));

        var restaurante2 = new Restaurante();
        restaurante2.setNome("Sabor Paulista");
        restaurante2.setTaxaFrete(new BigDecimal(0.89));

        repository.save(restaurante);
        repository.save(restaurante2);
    }
}
