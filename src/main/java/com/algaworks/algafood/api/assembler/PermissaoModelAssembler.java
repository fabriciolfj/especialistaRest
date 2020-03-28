package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao p){
        return modelMapper.map(p, PermissaoModel.class);
    }

    public List<PermissaoModel> toListModel(List<Permissao> permissoes){
        return permissoes.stream().map(p -> toModel(p)).collect(Collectors.toList());
    }
}
