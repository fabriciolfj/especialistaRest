package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CadastroRestauranteService {

    private static final String COZINHA_NAO_ENCONTRADA = "Cozinha não localizada parao id %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    public List<UsuarioModel> getResponsaveis(Long restauranteId){
        return findById(restauranteId).getUsuarios().stream().map(m -> usuarioAssembler.toModel(m)).collect(Collectors.toList());
    }

    public UsuarioModel getResponsavel(Long restauranteId, Long usuarioId){
        return findById(restauranteId)
                .getUsuarios().stream()
                .filter(f -> f.getId().equals(usuarioId))
                .map(m -> usuarioAssembler.toModel(m))
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long usuarioId){
        var restaurante = findById(restauranteId);
        var usuario = cadastroUsuarioService.getUsuario(usuarioId);
        restaurante.addUsuario(usuario);
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long usuarioId){
        Optional.of(findById(restauranteId)).map(restaurante -> restaurante.getUsuarios().stream().filter(p -> p.getId().equals(usuarioId))
                .findFirst().map(usr -> {
                    restaurante.removeUsuario(usr);
                    return usr;
                }).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId)));

        //restaurante.removeUsuario(usuario);
        /*Optional.of(findById(restauranteId))
                .map(r -> {
                    r.getUsuarios().stream().filter(f -> f.getId().equals(usuarioId)).map(result -> {
                        System.out.println(result);
                        r.removeUsuario(result);
                        return result;
                    });
                    return r;
                });*/
    }

    @Transactional
    public Restaurante atualizar(Restaurante restaurante){
        try{
            var cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
            var cidade = cadastroCidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
            restaurante.getEndereco().setCidade(cidade);
            restaurante.setCozinha(cozinha);
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        try{
            var cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
            var cidade = cadastroCidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
            restaurante.getEndereco().setCidade(cidade);
            restaurante.setCozinha(cozinha);
            restaurante.setAtivo(true);
            restaurante.setAberto(true);
            return restauranteRepository.save(restaurante);
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    public void fechar(Long restauranteIid){
        var restaurante = findById(restauranteIid);
        restaurante.setAberto(false);
    }

    @Transactional
    public void ativar(Long restauranteId){
        var restaurante = findById(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        var restaurante = findById(restauranteId);
        restaurante.inativar();
    }

    public List<Restaurante> findAll(){
        return restauranteRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    public Restaurante findById(Long id){
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = findById(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.findById(formaPagamentoId);
        restaurante.removeFormaPagto(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = findById(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.findById(formaPagamentoId);
        restaurante.addFormaPagamento(formaPagamento);
    }

    public Produto findProduto(Long restauranteId, Long produtoId){
        return restauranteRepository.findByProduto(restauranteId, produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    @Transactional
    public void abrir(Long restauranteId) {
        var restaurante = findById(restauranteId);
        restaurante.setAberto(true);
    }

    @Transactional
    public void ativar(List<Long> restauranteIds){
        try{
            restauranteIds.forEach(this::ativar);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional //coloco aqui, embora inativar tenha uma anotação transactional, para garantir se algum der erro, faça rollback de todos os registros
    public void inativar(List<Long> restauranteIds){
        try{
            restauranteIds.forEach(this::inativar);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }
}
