import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerEndereco;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.Configuration;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import repositories.EnderecoRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerEndereco enderecoCtrl = new ControllerEndereco(enderecoRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) enderecoCtrl;
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
    public void buscaPorIdTest() {
        Logger.info("busca endereço por id test");

        FakeRequest request = new FakeRequest(GET, "/api/enderecos/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void buscaPorCepTest() {
        Logger.info("busca endereços por cep test");

        FakeRequest request = new FakeRequest(GET, "/api/enderecos/cep/85864150");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void buscaTodosTest() {
        Logger.info("busca todos os endereços test");

        FakeRequest request = new FakeRequest(GET, "/api/enderecos");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }
}
