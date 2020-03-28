package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toObject(ProdutoInput input){
        return modelMapper.map(input, Produto.class);
    }

    public void copytInputToObject(ProdutoInput input, Produto produto){
        modelMapper.map(input, produto);
    }
}
