import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import play.Configuration;
import play.Logger;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private Configuration configuration;

    FakeApplication app;

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
    public void chamadaDoIndex() {

        Logger.info("Testa se o index retorna OK");

        FakeRequest request = new FakeRequest(GET, "/");
        Result result = route(request);

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo(null);
        assertThat(charset(result)).isEqualTo(null);
        assertThat(contentAsString(result)).isEmpty();
    }
}
