import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Configuration;
import play.test.Helpers;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class DatabaseTest {
	
	private static Configuration additionalConfigurations;

	public static play.test.FakeApplication app;
	
	@BeforeClass
	public static void startApp() {

		Config additionalConfig = ConfigFactory.parseFile(new File("conf/test.conf"));

		additionalConfigurations = new Configuration(additionalConfig);

		app = Helpers.fakeApplication(additionalConfigurations.asMap());

		Helpers.start(app);
	}
	
	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}

    @Test
    public void TesteSimples() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

}
