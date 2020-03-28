package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.fasterxml.jackson.annotation.JsonIgnore;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank//(groups = {Groups.CozinhaId.class})
    @Column(nullable = false)
    private String nome;

    //@DecimalMin("1")
    //@PositiveOrZero//(message="{TaxaFrete.invalida}")gg
    //@Multiplo(numero = 5)
    //@TaxaFrete
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@NotNull//(groups = {Groups.CozinhaId.class})
    //@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    //@Valid
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name="restaurante_id"),
            inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurante_usuario_responsavel",
               joinColumns = @JoinColumn(name = "restaurante_id"),
               inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> usuarios = new HashSet<>();

    private boolean ativo = Boolean.TRUE;

    private boolean aberto;

    public void ativar(){
        setAtivo(true);
    }

    public void inativar(){
        setAtivo(false);
    }

    public void addFormaPagamento(FormaPagamento pgto){
        this.formasPagamento.add(pgto);
        //if(this.formasPagamento.stream().filter(f -> f.getId().equals(pgto.getId())).count() == 0){
        //}
    }

    public void removeFormaPagto(FormaPagamento pgto){
        this.formasPagamento.remove(pgto);
    }

    public void addProduto(Produto produto){
        this.produtos.add(produto);
    }

    public void addUsuario(Usuario usuario){
        this.usuarios.add(usuario);
    }

    public void removeUsuario(Usuario usuario){
        this.usuarios.remove(usuario);
    }

    public Optional<FormaPagamento> getFormaPagamento(Long formaPagamentoId) {
        return this.formasPagamento
                .stream()
                .filter(f -> f.getId().equals(formaPagamentoId))
                .findFirst();
    }

    /*public Produto findProduto(Long id){
        return this.produtos.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }*/

}
