package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toObject(PedidoInput input){
        return modelMapper.map(input, Pedido.class);
    }
}
