package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static com.algaworks.algafood.domain.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.domain.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RequestMapping
@RestController
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("teste/cozinhas")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Cozinha> findByName(@RequestParam("nome") String nome){
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("teste/todasCozinhas")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Cozinha>  likeNome(@RequestParam("nome") String nome){
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("teste/taxas")
    public List<Restaurante> entreTaxas(@RequestParam(value = "taxaInicial", defaultValue = "0")BigDecimal taxaInicial,
                                        @RequestParam(value = "taxaFinal", defaultValue = "0") BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("teste/restauranteCozinha")
    public List<Restaurante> restauranteCozinha(@RequestParam(value = "nome") String nome,
                                                @RequestParam(value = "idCozinha") Long idCozinha){
        return restauranteRepository.consultaPorNome(nome, idCozinha);
    }

    @GetMapping("/teste/top2")
    public List<Restaurante> restauratesTop2PorNome(@RequestParam(value = "nome") String nome){
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/teste/exists")
    public boolean cozinhaExists(@RequestParam("nome") String nome){
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/teste/countCozinha")
    public int restaurantesCountCozinha(Long cozinhaId){
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/teste/restaurantes")
    public List<Restaurante> find(@RequestParam(value ="nome",required = false) String nome,
                                  @RequestParam(value = "taxaInicial", required = false) BigDecimal taxaInicial,
                                  @RequestParam(value ="taxaFinal",required = false) BigDecimal taxaFinal){
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/teste/restaurantes-frete-gratis")
    public List<Restaurante> find(@RequestParam(value ="nome",required = false) String nome){
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/teste/restaurantes/primeiro")
    public Optional<Restaurante> primeiroRestaurante(){
        return restauranteRepository.buscarPrimeiro();
    }

}
