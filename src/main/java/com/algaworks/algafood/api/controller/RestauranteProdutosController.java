package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoInputDesassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.ProdutoModel;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequestMapping("/restaurantes/{restauranteId}/produtos")
@RestController
@RequiredArgsConstructor
public class RestauranteProdutosController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDesassembler produtoInputDesassembler;
    private final CadastroProdutoService cadastroProdutoService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProdutoModel> getListProdutos(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos){
        var restaurante = cadastroRestauranteService.findById(restauranteId);
        var produtos = incluirInativos? cadastroProdutoService.findByRestaurante(restaurante) : cadastroProdutoService.findAtivoProdutosRestaurante(restaurante);
        return produtoModelAssembler.toListModel(produtos);
    }

    @GetMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProdutoModel findProdutoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        var produto = cadastroRestauranteService.findProduto(restauranteId, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.RESTAURANTE.GERENCIAR_FUNCIONAMENTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduto(@PathVariable Long restauranteId,@Valid @RequestBody ProdutoInput input){
        var produto = produtoInputDesassembler.toObject(input);
        var resturante = cadastroRestauranteService.findById(restauranteId);
        produto.setRestaurante(resturante);
        cadastroProdutoService.save(produto);
    }

    @CheckSecurity.RESTAURANTE.GERENCIAR_FUNCIONAMENTO
    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId,@Valid @RequestBody ProdutoInput input){
        var produtoAtual = cadastroRestauranteService.findProduto(restauranteId, produtoId);
        produtoInputDesassembler.copytInputToObject(input, produtoAtual);
        cadastroProdutoService.save(produtoAtual);
    }

}
