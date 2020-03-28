package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaEstadoMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        var estados = context.getBean(EstadoRepository.class);

        /*var est = estados.findAll();
        est.stream().forEach(p ->
            System.out.println("Nome: "+ p.getNome() + " Total cidades: " + p.getCidades().size())
        );*/
    }
}
