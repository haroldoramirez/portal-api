import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerEstudante;
import controllers.ControllerExperiencia;
import models.*;
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
import repositories.ExperienciaRepository;

import java.io.File;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

@RunWith(MockitoJUnitRunner.class)
public class ExperienciaControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private ExperienciaRepository experienciaRepository;

    @Mock
    private EstudanteRepository estudanteRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerEstudante estudanteCtrl = new ControllerEstudante(estudanteRepository);

        final ControllerExperiencia experienciaCtrl = new ControllerExperiencia(experienciaRepository, estudanteRepository);

        final GlobalSettings global = new GlobalSettings() {

            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) experienciaCtrl;
            }

        };

        final GlobalSettings global2 = new GlobalSettings() {
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
    public void inserirEstudanteTest() {

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
    public void inserirTest() {
        Logger.info("inserir experiencia test");

        this.inserirEstudanteTest();

        String stringJson = "{\n" +
                "    \"dataInicio\": \"2014-10-14\",\n" +
                "    \"dataTermino\": \"2014-10-14\",\n" +
                "    \"empresa\": \"Empresa Test2\",\n" +
                "    \"cargo\": \"Cargo Técnico2\",\n" +
                "    \"atividade\": \"Atividade Test2\",\n" +
                "    \"vinculo\": \"Vínculo Test2\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes/1/experiencias").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
        assertThat(contentAsString(result)).contains("Empresa Test2");
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar Experiencia test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"dataInicio\": \"2014-10-14\",\n" +
                "    \"dataTermino\": \"2014-10-14\",\n" +
                "    \"empresa\": \"Empresa Test722\",\n" +
                "    \"cargo\": \"Cargo Técnico472\",\n" +
                "    \"atividade\": \"Atividade Test247\",\n" +
                "    \"vinculo\": \"Vínculo Test2\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"3\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1/experiencias/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Empresa Test722");
    }

    @Test
    public void removerTest() {
        Logger.info("remover por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/experiencias/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerNaoEncontradoTest() {
        Logger.info("remover por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/experiencias/1897");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("experiencia não encontrada");
    }

    @Test
    public void buscaPorIdExperiencia() {
        Logger.info("busca experiencia por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/experiencias/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Empresa Test2");
    }

    @Test
    public void buscaPorIdExperienciaNaoEncontradoTest() {
        Logger.info("busca por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/experiencias/1456");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Experiencia não encontrada");
    }

    @Test
    public void buscaExperienciaPorIdEstudanteNaoEncontradoTest() {
        Logger.info("busca experiencia por id do estudante não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1654/experiencias/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante não encontrado");
    }
}
