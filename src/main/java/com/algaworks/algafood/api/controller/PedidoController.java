package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequestMapping("/pedidos")
@RestController
@RequiredArgsConstructor
public class PedidoController {

    private final EmissaoPedidoService cadastroPedidoService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PedidoResumoModel> findAll(PedidoFilter filter,@PageableDefault(size = 2) Pageable pageable){
        return cadastroPedidoService.findAll(filter, pageable);
    }

    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MappingJacksonValue findAll(@RequestParam(required = false) String campos){
        var pedidos = cadastroPedidoService.findAll();
        var pedidosWrapper = new MappingJacksonValue(pedidos);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if(StringUtils.isNotBlank(campos)){
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        pedidosWrapper.setFilters(filterProvider);
        return pedidosWrapper;
    }*/

    @GetMapping("/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoModel findById(@PathVariable String codigo){
        return cadastroPedidoService.buscarOuFalhar(codigo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel create(@RequestBody @Valid PedidoInput input){
        return cadastroPedidoService.create(input);
    }
}
