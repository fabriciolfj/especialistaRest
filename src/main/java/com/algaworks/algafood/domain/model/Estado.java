package com.algaworks.algafood.domain.model;


import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

    @Id
    @NotNull(groups = Groups.EstadoId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;
}
