package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.FormaPagamentoDescricaoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPgtoDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoDescricaoInput input){
        return modelMapper.map(input, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoDescricaoInput input, FormaPagamento formaPagamento){
        modelMapper.map(input, formaPagamento);
    }
}
