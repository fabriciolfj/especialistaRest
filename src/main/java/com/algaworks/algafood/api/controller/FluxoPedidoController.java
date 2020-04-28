package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigo) {
        fluxoPedido.confirmar(codigo);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable String codigo) {
        fluxoPedido.cancelar(codigo);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigo) {
        fluxoPedido.entregar(codigo);
        return ResponseEntity.noContent().build();
    }
}
