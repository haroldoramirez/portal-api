import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerEstudanteConhecimentoInformatica;
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
import repositories.EstudanteConhecimentoInformaticaRepository;
import repositories.EstudanteRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class EstudanteConhecimentoInformaticaControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private EstudanteConhecimentoInformaticaRepository estudanteConhecimentoInformaticaRepository;

    @Mock
    private EstudanteRepository estudanteRepository;

    @Mock
    private ConhecimentoInformaticaRepository conhecimentoInformaticaRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerEstudanteConhecimentoInformatica estudanteConhecimentoInformaticaCtrl =
                new ControllerEstudanteConhecimentoInformatica(estudanteConhecimentoInformaticaRepository, estudanteRepository, conhecimentoInformaticaRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) estudanteConhecimentoInformaticaCtrl;
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
    public void inserirConhecimentoInformaticaTest() {
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
    public void inserirTest() {
        Logger.info("insere estudanteconhecimentoinformatica Test");

        this.inserirEstudanteTest();
        this.inserirGrupoConhecimentoTest();
        this.inserirConhecimentoInformaticaTest();

        String jsonString = "{\n" +
                "    \"nivel\": \"5\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"conhecimentoInformatica\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(jsonString);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes/1/informatica").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
        assertThat(contentAsString(result)).contains("5");
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualiza estudanteconhecimentoinformatica Test");

        this.inserirTest();

        String jsonString = "{\n" +
                "    \"nivel\": \"2\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    },\n" +
                "    \"conhecimentoInformatica\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(jsonString);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1/informatica/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("2");
    }

    @Test
    public void removerTest() {
        Logger.info("remover por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerEstudanteNaoEncontradoTest() {
        Logger.info("remover não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/223/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante não encontrado");
    }

    @Test
    public void removerEstudanteConhecimentoInformaticaNaoEncontradoTest() {
        Logger.info("remover não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/informatica/1345");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante conhecimento informatica não encontrado");
    }

    @Test
    public void buscaPorIdEstudante() {
        Logger.info("busca estudanteconhecimentoinformatica por Id do Estudante Test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/informatica");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Haroldo Ramirez");
    }

    @Test
    public void buscaPorIdInformatica() {
        Logger.info("busca todos estudanteconhecimentoinformatica do estudante Test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/informatica/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("5");
    }

}
