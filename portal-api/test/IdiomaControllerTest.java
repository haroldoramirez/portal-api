import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerIdioma;
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
import repositories.IdiomaRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class IdiomaControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private IdiomaRepository idiomaRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerIdioma idiomaCtrl = new ControllerIdioma(idiomaRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) idiomaCtrl;
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
        Logger.info("salvar idioma test");

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
    public void atualizarTest() {
        Logger.info("atualizar idioma test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"nomeIdioma\": \"Inglês\"\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/idiomas/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void atualizarIdiomaNaoEncontradoTest() {
        Logger.info("atualizar idioma Nao Encontrado test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"25\",\n" +
                "    \"nomeIdioma\": \"Espanhol\"\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/idiomas/25").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("idioma não encontrado");
    }

    @Test
    public void atualizarSemParametroIdTest() {
        Logger.info("atualizar sem parâmetro Id test");

        this.inserirTest();

        String stringJson = "{\"nomeIdioma\": \"Espanhol\"}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/idiomas/1").withJsonBody(json);
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
                "    \"nomeIdioma\": \"Inglês\"\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/idiomas/155").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).contains("dados inconsistentes");
    }

    @Test
    public void buscaPorIdTest() {
        Logger.info("busca por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/idiomas/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Português");
    }

    @Test
    public void buscaPorIdNaoEncontradoTest() {
        Logger.info("busca por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/idiomas/9");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("idioma não encontrado");
    }

    @Test
    public void buscaTodosTest() {
        Logger.info("busca todos os test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/idiomas");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }
}
