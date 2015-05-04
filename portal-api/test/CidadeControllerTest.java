import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.ControllerCidade;
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
import repositories.CidadeRepository;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class CidadeControllerTest {

    private Configuration configuration;

    FakeApplication app;

    @Mock
    private CidadeRepository cidadeRepository;

    @Before
    public void startApp() {
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

        configuration = new Configuration(additionalConfig);

        final ControllerCidade cidadeCtrl = new ControllerCidade(cidadeRepository);

        final GlobalSettings global = new GlobalSettings() {
            @Override
            public <A> A getControllerInstance(Class<A> aClass) throws Exception {
                return (A) cidadeCtrl;
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
        Logger.info("busca cidade por id test");

        FakeRequest request = new FakeRequest(GET, "/api/cidades/1");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void buscaPorUfTest() {
        Logger.info("busca cidade por Uf test");

        FakeRequest request = new FakeRequest(GET, "/api/cidades/estado/PR");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void buscaTodosTest() {
        Logger.info("busca todas as cidades test");

        FakeRequest request = new FakeRequest(GET, "/api/cidades");
        Result result = route(request);

        assertThat(status(result)).isNotNull();
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(status(result)).isEqualTo(OK);
    }
}
