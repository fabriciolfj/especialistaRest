package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoResumoModelAssembler {

    private final ModelMapper modelMapper;

    public PedidoResumoModel toModel(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toListModel(List<Pedido> pedidos){
        return pedidos.stream().map(m -> toModel(m)).collect(Collectors.toList());
    }

}
