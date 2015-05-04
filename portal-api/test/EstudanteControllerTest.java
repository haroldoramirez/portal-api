import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerEstudante;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.Configuration;
import play.GlobalSettings;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import repositories.EstudanteRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class EstudanteControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private EstudanteRepository estudanteRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerEstudante estudanteCtrl = new ControllerEstudante(estudanteRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) estudanteCtrl;
            }
        };

        app = fakeApplication(configuration.asMap());

        start(app);

        start(fakeApplication(inMemoryDatabase()));
    }

    @After
    public void stopApp() {
        stop(app);
    }

    @Test
    public void inserirTest() {

        Logger.info("insere Estudante Test");

        String stringJson = "{\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123\",\n" +
                "    \"dataNascimento\": \"1986-12-02\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
        assertThat(contentAsString(result)).contains("Haroldo Ramirez");
    }

    @Test
    public void inserirSemCpfTest() {
        Logger.info("insere Estudante sem CPF Test");

        String stringJson = "{\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez da Nóbrega\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).contains("Você precisa inserir o cpf");
    }

    @Test
    public void inserirSemEmailTest() {
        Logger.info("insere Estudante sem Email Test");

        String stringJson = "{\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez da Nóbrega\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123387654\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar Estudante test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez da Nóbrega\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123387654\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Haroldo Ramirez da Nóbrega");
    }

    @Test
    public void atualizarEstudanteNaoEncontradoTest() {
        Logger.info("atualizar Estudante Nao Encontrado test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"5\",\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez da Nóbrega\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123387654\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/5").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("estudante não encontrado");
    }

    @Test
    public void atualizarSemParametroIdTest() {
        Logger.info("atualizar sem parâmetro Id test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"nomeCompleto\": \"Haroldo\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123387654\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).contains("falta o parâmetro id");
    }

    @Test
    public void atualizarComDadosInconsistentesTest() {
        Logger.info("atualizar com dados inconsistentes test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"nomeCompleto\": \"Haroldo Ramirez da Nóbrega\",\n" +
                "    \"nomeDaMae\": \"Adelia\",\n" +
                "    \"cpf\": \"31123123387654\",\n" +
                "    \"dataNascimento\": \"1999-12-19\",\n" +
                "    \"nacionalidade\": \"Brasileiro\",\n" +
                "    \"dataAtualizacao\": \"2014-02-19\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"estadoCivil\": \"SOLTEIRO\",\n" +
                "    \"trabalhando\": \"SIM\",\n" +
                "    \"usuario\": {\n" +
                "        \"login\": \"haroldo\",\n" +
                "        \"email\": \"haroldoramirez@gmail.com\",\n" +
                "        \"senha\": \"123456789\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/50").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).contains("dados inconsistentes");
    }

    @Test
    public void removerTest() {
        Logger.info("remover por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerNaoEncontradoTest() {
        Logger.info("remover por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1000");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("estudante não encontrado");
    }

    @Test
    public void buscaPorIdTest() {
        Logger.info("busca por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Haroldo");
    }

    @Test
    public void buscaPorIdNaoEncontradoTest() {
        Logger.info("busca por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/6");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("estudante não encontrado");
    }

    @Test
    public void buscaTodosTest() {
        Logger.info("busca todos test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

}