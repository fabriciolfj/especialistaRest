package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.EstadoNameInput;
import com.algaworks.algafood.domain.model.Estado;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(final EstadoNameInput estadoNameInput){
        return modelMapper.map(estadoNameInput, Estado.class);
    }

    public void copyToDomainObject(EstadoNameInput estadoNameInput, Estado estado){
        modelMapper.map(estadoNameInput, estado);
    }
}
