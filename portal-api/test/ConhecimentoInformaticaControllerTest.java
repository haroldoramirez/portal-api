import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerConhecimentoInformatica;
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
import repositories.ConhecimentoInformaticaRepository;
import repositories.GrupoConhecimentoRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConhecimentoInformaticaControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private GrupoConhecimentoRepository grupoConhecimentoRepository;

    @Mock
    private ConhecimentoInformaticaRepository conhecimentoInformaticaRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerConhecimentoInformatica conhecimentoInformaticaCtrl =
                new ControllerConhecimentoInformatica(conhecimentoInformaticaRepository, grupoConhecimentoRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) conhecimentoInformaticaCtrl;
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
    public void inserirGrupoConhecimentoTest() {
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
    public void inserirTest() {
        Logger.info("insere conhecimentoInformatica test");

        this.inserirGrupoConhecimentoTest();

        String jsonString = "{\n" +
                "    \"descricao\": \"Libre Office\",\n" +
                "    \"grupoConhecimento\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(jsonString);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/grupos/1/informatica").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar conhecimento Informática test");

        this.inserirTest();

        String jsonString = "{\n" +
                "    \"descricao\": \"Microsoft Word\",\n" +
                "    \"grupoConhecimento\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(jsonString);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/grupos/1/informatica/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Microsoft Word");
    }

    @Test
    public void removerTest() {
        Logger.info("remover conhecimento Informática por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/grupos/1/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerGrupoConhecimentoNaoEncontradoTest() {
        Logger.info("remover grupo conhecimento por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/grupos/1456/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Grupo Conhecimento não encontrado");
    }

    @Test
    public void removerConhecimentoInformaticaNaoEncontradoTest() {
        Logger.info("remover conhecimento Informática por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/grupos/1/informatica/1656");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Conhecimento Informatica não encontrado");
    }

    @Test
    public void buscaPorIdGrupoTest() {
        Logger.info("busca conhecimento Informática por id grupo test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/grupos/1/informatica");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Libre Office");
    }

    @Test
    public void buscaPorIdConhecimentoInformatica() {
        Logger.info("busca conhecimento Informática test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/grupos/1/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Libre Office");
    }
}
