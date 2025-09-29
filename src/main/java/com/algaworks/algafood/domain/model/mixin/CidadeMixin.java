package com.algaworks.algafood.domain.model.mixin;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
