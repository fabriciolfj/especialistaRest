package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/pedidos/{codigo}")
@RestController
@RequiredArgsConstructor
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigo) {
        fluxoPedido.confirmar(codigo);
    }

    @PutMapping("/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigo) {
        fluxoPedido.cancelar(codigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigo) {
        fluxoPedido.entregar(codigo);
    }
}
