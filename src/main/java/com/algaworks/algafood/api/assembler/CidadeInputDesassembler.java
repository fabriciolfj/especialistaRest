package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(final CidadeInput cidadeNomeInput){
        return modelMapper.map(cidadeNomeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade){
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }
}
