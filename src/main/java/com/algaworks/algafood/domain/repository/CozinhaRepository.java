package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findByNome(String nome);

    List<Cozinha> findTodasByNomeContaining(String nome);

    boolean existsByNome(String nome);
}
