package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.GrupoNomeInput;
import com.algaworks.algafood.domain.model.Grupo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toObject(GrupoNomeInput input){
        return modelMapper.map(input, Grupo.class);
    }

    public void copyToObject(GrupoNomeInput input, Grupo grupo){
        modelMapper.map(input, grupo);
    }
}
