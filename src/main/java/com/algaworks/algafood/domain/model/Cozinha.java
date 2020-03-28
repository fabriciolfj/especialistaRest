package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonRootName("cozinha")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Cozinha {

    @EqualsAndHashCode.Include
    @Id
    //@NotNull(groups = {Groups.CozinhaId.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    //@JsonProperty("titulo")
    private String nome;

    @OneToMany(mappedBy = "cozinha")
    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
