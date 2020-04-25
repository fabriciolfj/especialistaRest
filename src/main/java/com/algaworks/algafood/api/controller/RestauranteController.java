package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPgtoDesassembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDesassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoDescricaoInput;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*", maxAge = 10) //limpar o cache em 10 segundos
@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

    @Autowired
    private SmartValidator validator;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDesassembler restauranteInpuDesAssembler;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private FormaPgtoDesassembler formaPgtoDesassembler;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds){
        restauranteService.ativar(restauranteIds);
    }

    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds){
        restauranteService.inativar(restauranteIds);
    }

    @GetMapping("/{restauranteId}/responsaveis")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioModel> getResponsaveis(@PathVariable Long restauranteId){
        return restauranteService.getResponsaveis(restauranteId);
    }

    @GetMapping("/{restauranteId}/responsaveis/{responsavelId}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModel getResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        return restauranteService.getResponsavel(restauranteId, responsavelId);
    }

    @DeleteMapping("/{restauranteId}/responsaveis/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.removerResponsavel(restauranteId, responsavelId);
    }

    @PutMapping("/{restauranteId}/responsaveis/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarReponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.adicionarResponsavel(restauranteId, responsavelId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId){
        restauranteService.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId){
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        restauranteService.ativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativo(@PathVariable Long restauranteId){
        restauranteService.inativar(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(/*@Validated(Groups.CozinhaId.class)*/ @Valid @RequestBody RestauranteInput restauranteInput){
        var restaurante = restauranteInpuDesAssembler.toDomanObject(restauranteInput);
        return restauranteModelAssembler.toModel(restauranteService.salvar(restaurante));
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(@Valid @RequestBody RestauranteInput restauranteInput, @PathVariable("id")Long id){
        var restauranteAtual = restauranteService.findById(id);
        //var restaurante = restauranteInpuDesAssembler.toDomanObject(restauranteInput);
        //BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        restauranteInpuDesAssembler.copyToDomainObject(restauranteInput, restauranteAtual);
        return restauranteModelAssembler.toModel(restauranteService.atualizar(restauranteAtual));
    }

    @GetMapping("/{id}")
    public RestauranteModel findById(@PathVariable("id") Long id) {
        var restaurante = restauranteService.findById(id);
        return restauranteModelAssembler.toModel(restaurante);
    }

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> findAll() {
        var list = restauranteService.findAll();
        return restauranteModelAssembler.toCollectionModel(list);
        /*return ResponseEntity
                .ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8000")
                .body(restauranteModelAssembler.toCollectionModel(list));*/
    }

    /*@JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> findAllName() {
        return findAll();
    }*/

    @PatchMapping("/{id}")
    public RestauranteModel atualizarParcial(@PathVariable("id") Long id, @RequestBody Map<String, Object> restaurante, HttpServletRequest request){
        var restauranteAtual = restauranteService.findById(id);
        merge(restaurante, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return restauranteModelAssembler.toModel(restauranteService.atualizar(restauranteAtual));
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if(bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(@RequestBody Map<String, Object> campos, Restaurante restaurante, HttpServletRequest request) {
        var servletRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

            campos.forEach((nomePropriedade, valorPropriedade) -> {
                var field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                var novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restaurante, novoValor);
            });
        }catch (IllegalArgumentException e){
            var cause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), cause, servletRequest);
        }
    }


}
