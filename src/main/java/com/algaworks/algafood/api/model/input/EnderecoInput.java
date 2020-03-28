package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.model.CidadeResumoModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    private String bairro;
    @NotNull
    private CidadeIdInput cidade;
}
