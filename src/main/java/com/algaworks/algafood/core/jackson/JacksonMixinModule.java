package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.mixin.CidadeMixin;
import com.algaworks.algafood.domain.model.mixin.RestauranteMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

/*@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 7197240732870772798L;

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}*/
