import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerCadastro;
import models.Cadastro;
import models.Usuario;
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
import repositories.CadastroRepository;
import repositories.EstudanteRepository;
import repositories.UsuarioRepository;

import java.io.File;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class CadastroControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private CadastroRepository cadastroRepository;

    @Mock
    private EstudanteRepository estudanteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerCadastro cadastroCtrl = new ControllerCadastro(estudanteRepository, cadastroRepository, usuarioRepository);

        final GlobalSettings global = new GlobalSettings() {

            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) cadastroCtrl;
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
        Logger.info("insere cadastro Test");

        Usuario usuario = new Usuario();
        usuario.setEmail("fulano@fulano.com");
        usuario.setLogin("fulanoTest");
        usuario.setSenha("123456");

        Cadastro cadastro = new Cadastro();
        cadastro.setUsuario(usuario);
        cadastro.setValidade(new Date());

        JsonNode json = Json.toJson(cadastro);
        System.out.print(cadastro);

        FakeRequest request = new FakeRequest(POST, "/api/cadastro").withJsonBody(json);
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(CREATED);
    }
}
