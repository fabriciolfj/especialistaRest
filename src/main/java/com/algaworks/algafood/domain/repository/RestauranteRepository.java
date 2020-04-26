package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    //@Query("From Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
    List<Restaurante> consultaPorNome(String nome, @Param("cozinhaId") Long cozinhaId);

    //List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
                              /*aqui e opcional antes do by e depois do find*/
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome); //quero so os primeiros 2 registros

    int countByCozinhaId(Long cozinha);

    @Query("Select p From Produto p join p.restaurante r where r.id = :restauranteId and p.id = :produtoId")
    Optional<Produto> findByProduto(Long restauranteId, Long produtoId);

    @Query("select case when count(1) > 0 then true else false end From Restaurante rest join rest.usuarios resp where resp.id = :usuarioId and rest.id = :restauranteId")
    boolean existsResponsavel(Long restauranteId, Long usuarioId);
}
