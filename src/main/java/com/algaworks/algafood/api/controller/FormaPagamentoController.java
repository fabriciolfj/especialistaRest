package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPgtoDesassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoDescricaoInput;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPgtoDesassembler desassembler;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel create(@RequestBody FormaPagamentoDescricaoInput input){
        var formaPgto = desassembler.toDomainObject(input);
        return assembler.toModel(cadastroFormaPagamentoService.save(formaPgto));
    }

    @PutMapping("/{formaPgtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long formaPgtoId, @RequestBody FormaPagamentoDescricaoInput input){
        var formaPgto = cadastroFormaPagamentoService.findById(formaPgtoId);
        desassembler.copyToDomainObject(input, formaPgto);
        cadastroFormaPagamentoService.save(formaPgto);
    }

    @DeleteMapping("/{formaPgtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long formaPgtoId){
        cadastroFormaPagamentoService.delete(formaPgtoId);
    }

}
