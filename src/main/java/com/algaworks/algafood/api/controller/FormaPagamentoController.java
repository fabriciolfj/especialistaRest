package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPgtoDesassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoDescricaoInput;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPgtoDesassembler desassembler;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FormaPagamentoModel> list() {
        return assembler.toListModel(cadastroFormaPagamentoService.list());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel create(@Valid @RequestBody FormaPagamentoDescricaoInput input){
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
