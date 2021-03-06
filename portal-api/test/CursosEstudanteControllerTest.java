import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerCursosEstudante;
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
import repositories.CursosEstudanteRepository;
import repositories.EstudanteRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class CursosEstudanteControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private CursosEstudanteRepository cursosEstudanteRepository;

    @Mock
    private EstudanteRepository estudanteRepository;

    @Before
    public void startApp() {

        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerCursosEstudante cursosEstudanteCtrl = new ControllerCursosEstudante(cursosEstudanteRepository, estudanteRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) cursosEstudanteCtrl;
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

        Logger.info("inserir Cursos Estudante test");

        this.inserirEstudanteTest();

        String stringJson = "{\n" +
                "    \"nome\": \"Ciência da Computação\",\n" +
                "    \"instituicao\": \"Unioeste\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(POST, "/api/estudantes/1/cursos").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }

    @Test
    public void atualizarTest() {
        Logger.info("atualizar cursos Estudante test");

        this.inserirTest();

        String stringJson = "{\n" +
                "    \"nome\": \"Pedagogia\",\n" +
                "    \"instituicao\": \"Unioeste\",\n" +
                "    \"estudante\": {\n" +
                "        \"id\": \"1\"\n" +
                "    }\n" +
                "}";

        JsonNode json = Json.parse(stringJson);
        System.out.print(json);

        FakeRequest request = new FakeRequest(PUT, "/api/estudantes/1/cursos/1").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Pedagogia");
    }

    @Test
    public void removerTest() {
        Logger.info("remover cursosestudante por id test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/cursos/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void removerCursoNaoEncontradoTesT() {
        Logger.info("remover por id não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/1/cursos/2356");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Curso Estudante não encontrado");
    }

    @Test
    public void removerCursoEstudanteNaoEncontradoTest() {
        Logger.info("remover cursos do estudante não encontrado test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(DELETE, "/api/estudantes/23451/cursos/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(NOT_FOUND);
        assertThat(contentAsString(result)).contains("Estudante não encontrado");
    }

    @Test
    public void buscaPorIdEstudante() {
        Logger.info("busca cursos pelo id do estudante test");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/cursos");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Haroldo Ramirez");
    }

    @Test
    public void buscaPorIdCurso() {
        Logger.info("busca pelo id do cursos");

        this.inserirTest();

        FakeRequest request = new FakeRequest(GET, "/api/estudantes/1/cursos/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains("Unioeste");
    }
}
