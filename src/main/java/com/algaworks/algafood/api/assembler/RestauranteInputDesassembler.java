package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestauranteInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomanObject(RestauranteInput restauranteInput){
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante){
        //Para evitar trocar o id da cozinha, pois o jpa está entendendo isso. e nao verdade estou mudando a cozinha no restaurante.
        restaurante.setCozinha(new Cozinha());

        Optional.ofNullable(restaurante.getEndereco()).map(r -> {
            r.setCidade(new Cidade());
            return r;
        });

        modelMapper.map(restauranteInput, restaurante);
    }
}
