import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Configuration;
import play.mvc.Result;
import play.test.FakeApplication;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class RotasTest {

    private Configuration configuration;

    public FakeApplication app;

    @Before
    public void startApp() {

        Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));
        configuration = new Configuration(additionalConfig);

        app = fakeApplication(configuration.asMap());

        start(app);

        start(fakeApplication(inMemoryDatabase()));
    }

    @After
    public void stopApp() {
        stop(app);
    }

    @Test
    public void badRoute() {
        Result result = route(fakeRequest(GET, "/api/test"));
        assertThat(result).isNull();
    }

    @Test
    public void estudanteRota() {
        Result result = route(fakeRequest(GET, "/api/estudantes"));
        assertThat(result).isNotNull();
    }

    @Test
    public void idiomaRota() {
        Result result = route(fakeRequest(GET, "/api/idioma"));
        assertThat(result).isNotNull();
    }

    @Test
    public void estadoRota() {
        Result result = route(fakeRequest(GET, "/api/estados"));
        assertThat(result).isNotNull();
    }

    @Test
    public void enderecoRota() {
        Result result = route(fakeRequest(GET, "/api/enderecos"));
        assertThat(result).isNotNull();
    }


}