package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toObject(UsuarioInput input){
        return modelMapper.map(input, Usuario.class);
    }

    public void copyInputToObject(UsuarioInput input, Usuario usuario){
        modelMapper.map(input, usuario);
    }
}
