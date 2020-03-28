package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
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

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * ./mvnw verify quando uso o plugin maven-failsafe-plugin, para executar os testes de integração.
 * por default, classes com final IT, com esse plugin, ele nao roda
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml") //para user a application-test.yml
public class CadastroCozinhaIT {

	/*@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void deveCadastroCozinhaComDadosCorretos_QuandoDeveAtribuirId(){
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chineza");
		//ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCozinhaQuandoSemNome(){
		Cozinha novaCozinha = new Cozinha();
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalharQuandoExcluirCozinhaEmUso(){
		cadastroCozinhaService.excluir(1L);
	}

	@Test(expected = CozinhaNaoEncontradaException.class)
	public void deveFalharQuandoExcluirCozinhaInexistente(){
		cadastroCozinhaService.excluir(100L);
	}*/

	@LocalServerPort
	private int port;

	/*@Autowired
	private Flyway flyway;*/

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository repository;

	private int countCozinha;

	private String jsonCozinha;

	private Cozinha tailandesa = new Cozinha();

	private final int COZINHA_INEXISTENTE = 100;

	@Before
	public void setUp(){
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();

		jsonCozinha = ResourceUtils.getContentFromResource("/json/cozinha.json");
		//flyway.migrate(); pegar o arquivo testdata afterMigrate
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){
		given()
			.pathParam("cozinhaId", tailandesa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(tailandesa.getNome()));
	}

	@Test
	public void deveRetornarRespostaEStatus404_QuandoConsultarCozinhaInexistente(){
		given()
			.pathParam("cozinhaId", COZINHA_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas(){
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConterAsCozinhas_QuandoConsultarCozinhas(){
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(countCozinha))
			.body("nome", hasItems("Indiana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha(){
		given()
			.body(jsonCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	private void prepararDados(){
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Tailandesa");
		repository.save(cozinha);
		countCozinha++;

		tailandesa.setNome("Indiana");
		repository.save(tailandesa);
		countCozinha++;
	}


}
