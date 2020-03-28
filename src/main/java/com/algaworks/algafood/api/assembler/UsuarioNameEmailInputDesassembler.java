package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioNameEmailInput;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioNameEmailInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toObject(UsuarioNameEmailInput input){
        return modelMapper.map(input, Usuario.class);
    }

    public void copyInputToObject(UsuarioNameEmailInput input, Usuario usuario){
        modelMapper.map(input, usuario);
    }
}
