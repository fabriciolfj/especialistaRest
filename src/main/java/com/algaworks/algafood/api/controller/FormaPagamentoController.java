package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPgtoDesassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoDescricaoInput;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoModel>> list(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = cadastroFormaPagamentoService.getDataUltimaAtualizacao();

        if(request.checkNotModified(eTag)) {
            return null;
        }

        var list = assembler.toListModel(cadastroFormaPagamentoService.list());

        // Cache-Control: max-age=10
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(list);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> findById(@PathVariable Long formaPagamentoId) {
        var formaPagamento = assembler.toModel(cadastroFormaPagamentoService.findById(formaPagamentoId));

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) //pode armazenar em caches compartilhados.
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) a resposta pode ser armazenada em apenas caches locais
                //.cacheControl(CacheControl.noCache()) // sempre vai validar o etag, pois vai encarar a informação no cache como velha
                //.cacheControl(CacheControl.noStore()) não vai armazenar no cache
                .body(formaPagamento);
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

