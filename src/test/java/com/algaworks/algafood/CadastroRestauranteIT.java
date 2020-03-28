package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class CadastroRestauranteIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner  databaseCleaner;

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Before
    public void setUp(){
        RestAssured.basePath = "/restaurantes";
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepararBaseDados();
    }

    private void prepararBaseDados() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");

        cozinha = cozinhaRepository.save(cozinha);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Barretos");
        restaurante.setTaxaFrete(new BigDecimal(10.00));
        restaurante.setCozinha(cozinha);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Boi na brasa");
        restaurante2.setTaxaFrete(new BigDecimal(15.00));
        restaurante2.setCozinha(cozinha);

        repository.save(restaurante);
        repository.save(restaurante2);
    }

    @Test
    public void deveRetornarStatus200_ConsultaRestaurante(){
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornar2Resturantes_ConsultaRestaurante(){
        given()
           .accept(ContentType.JSON)
        .when()
          .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", hasSize(2));
    }
}
