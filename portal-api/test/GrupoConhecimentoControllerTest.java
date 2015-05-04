import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerGrupoConhecimento;
import models.GrupoConhecimento;
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
import repositories.GrupoConhecimentoRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class GrupoConhecimentoControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private GrupoConhecimentoRepository grupoConhecimentoRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));
        configuration = new Configuration(additionalConfig);
        final ControllerGrupoConhecimento grupoConhecimentoCtrl = new ControllerGrupoConhecimento(grupoConhecimentoRepository);
        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) grupoConhecimentoCtrl;
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
        Logger.info("insere grupoconhecimento test");

        String jsonString = "{\n" +
                "    \"descricao\": \"Teste\",\n" +
                "    \"posicao\": \"4\"\n" +
                "}";

        JsonNode json = Json.parse(jsonString);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/grupos").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar grupo conhecimento test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"descricao\": \"Teste\",\n" +
                "    \"posicao\": \"4\"\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/grupos/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void atualizarGrupoconhecimentoNaoEncontradoTest() {
        Logger.info("atualizar grupo conhecimento nao encontrado test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"id\": \"99\",\n" +
                "    \"descricao\": \"Teste\",\n" +
                "    \"posicao\": \"4\"\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.println(json);

        FakeRequest request = new FakeRequest(PUT, "/api/grupos/99").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("grupo conhecimento não encontrado");
    }

    @Test
    public void buscaPorIdTest() {
        Logger.info("busca grupoconhecimento por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/grupos/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void buscaPorIdNaoEncontradoTest() {
        Logger.info("busca por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/grupos/99");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("grupo conhecimento não encontrado");
    }

    @Test
    public void buscaTodosTest() {
        Logger.info("busca todos test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/grupos");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }
}
