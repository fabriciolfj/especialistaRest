package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.PedidoInputDesassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.repository.spec.PedidoSpecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoAssembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoInputDesassembler pedidoInputDesassembler;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroProdutoService cadastroProdutoService;
    private final CadastroCidadeService cadastroCidadeService;
    private final CadastroUsuarioService cadastroUsuarioService;

    private static final Logger LOG = LoggerFactory.getLogger(CadastroProdutoService.class);

    public PedidoModel create(PedidoInput input) {
        var pedido = pedidoInputDesassembler.toObject(input);
        pedido.setCliente(new Usuario(6L));
        validarDadosPedido(pedido);
        adicionarProdutos(pedido);
        pedido.calcularTotal();

        return pedidoAssembler.toModel( save(pedido));
    }

    public Pedido save(Pedido pedido) {
        return this.pedidoRepository.save(pedido);
    }

    private void adicionarProdutos(Pedido pedido) {
        Optional.of(pedido).map(p -> {
            p.getItens().stream().map(item -> {
                var produto = cadastroProdutoService.findById(item.getProduto().getId());
                item.setPrecoUnitario(produto.getPreco());
                item.setProduto(produto);
                item.calcularTotal();
                item.setPedido(p);
                return p;
            }).collect(Collectors.toList());
            return p;
        });
    }

    private void validarDadosPedido(Pedido pedido) {
        var restaurante = cadastroRestauranteService.findById(pedido.getRestaurante().getId());
        var formaPagamentoId = pedido.getFormaPagamento().getId();
        var cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        var formaPagamento = restaurante.getFormaPagamento(formaPagamentoId);
        var usuario = cadastroUsuarioService.getUsuario(pedido.getCliente().getId());

        if (formaPagamento.isEmpty()) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId, restaurante.getId());
        }

        pedido.setRestaurante(restaurante);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());
        pedido.setFormaPagamento(formaPagamento.get());
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(usuario);

        pedido.getItens().forEach(item -> {
            var id = item.getProduto().getId();

            restaurante.getProdutos().stream()
                    .filter(p -> {
                        LOG.info("Item do pedido sendo avaliado: " + id + " Produto do restaurante sendo avaliado: " + p.getId());
                        return p.getId().equals(id);
                    })
                    .findFirst()
                    .orElseThrow(() ->
                            new ProdutoNaoEncontradoException(id, restaurante.getId())
                    );
        });

    }

    public PedidoModel buscarOuFalhar(String codigo){
        return pedidoAssembler.toModel(findByCodigo(codigo));
    }

    public Pedido findByCodigo(String codigo){
        return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradaoException(codigo));
    }

    public Page<PedidoResumoModel> findAll(PedidoFilter filter, Pageable pageable) {
        pageable = traduzierPageable(pageable);
        var pedidosPageable = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);
        List<PedidoResumoModel> pedidos = pedidosPageable.stream().map(m -> pedidoResumoModelAssembler.toModel(m))
                .collect(Collectors.toList());
        Page<PedidoResumoModel> pedidosModel = new PageImpl<>(pedidos, pageable, pedidosPageable.getTotalElements());
        return pedidosModel;
    }

    private Pageable traduzierPageable(Pageable apiPageable){
        var mapeamento = Map.of(
                "codigo", "codigo,",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "valorTotal",
                "nomeCliente","cliente.nome",
                "taxaFrete", "taxaFrete");

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
