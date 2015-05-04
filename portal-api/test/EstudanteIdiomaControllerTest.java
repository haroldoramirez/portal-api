import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerEstudanteIdioma;
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
import repositories.EstudanteIdiomaRepository;
import repositories.EstudanteRepository;
import repositories.IdiomaRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class EstudanteIdiomaControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private EstudanteIdiomaRepository estudanteIdiomaRepository;

    @Mock
    private IdiomaRepository idiomaRepository;

    @Mock
    EstudanteRepository estudanteRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerEstudanteIdioma estudanteIdiomaCtrl =
                new ControllerEstudanteIdioma(estudanteIdiomaRepository, estudanteRepository, idiomaRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) estudanteIdiomaCtrl;
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
    public void inserirIdiomaTest() {
        Logger.info("insere idioma test");

        String stringJson = "{\"nomeIdioma\": \"Português\"}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/idiomas").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }

    @Test
    public void inserirTest() {
        Logger.info("insere EstudanteIdioma Test");

        this.inserirIdiomaTest();
        this.inserirEstudanteTest();

        String stringJson = "{\n" +
                "    \"le\": \"4\",\n" +
                "    \"fala\": \"3\",\n" +
                "    \"escreve\": \"5\",\n" +
                "    \"compreende\": \"5\",\n" +
                "    \"idioma\": {\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes/1/idiomas").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar estudanteidioma por id test");

        this.inserirIdiomaTest();
        this.inserirEstudanteTest();

        String stringJson = "{\n" +
                "    \"le\": \"5\",\n" +
                "    \"fala\": \"5\",\n" +
                "    \"escreve\": \"5\",\n" +
                "    \"compreende\": \"5\",\n" +
                "    \"idioma\": {\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1/idiomas/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("5");
    }

    @Test
    public void removerTest() {
        Logger.info("remover estudanteidioma por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/idiomas/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerNaoEncontradoTest() {
        Logger.info("remover por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/idiomas/1897");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante idioma não encontrado");
    }

    @Test
    public void buscaPorIdIdioma() {
        Logger.info("busca estudanteidioma por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/idiomas/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("3");
    }

    @Test
    public void buscaPorIdIdiomaNaoEncontradoTest() {
        Logger.info("busca estudante idioma por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/idiomas/1456");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Idioma não encontrado");
    }

    @Test
    public void buscaIdiomaPorIdEstudanteNaoEncontradoTest() {
        Logger.info("busca idioma por id do estudante não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1456/idiomas/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante não encontrado");
    }

}
