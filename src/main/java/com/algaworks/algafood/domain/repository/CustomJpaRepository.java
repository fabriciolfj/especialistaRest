package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean//spring jpa nao deve instanciar essa interface
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> buscarPrimeiro();

    void detach(T t);
}
