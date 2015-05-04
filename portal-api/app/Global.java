import actions.CorsAction;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.internalServerError;

public class Global extends GlobalSettings {

    static final String DEFAULT_PERSISTENCE_UNIT = "defaultPersistenceUnit";

    final private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    @Override
    public void onStart(Application application) {
        super.onStart(application);

        ctx.register(DatabaseConfiguration.class);
        ctx.scan("controllers");
        ctx.refresh();
    }

    @Override
    public void onStop(Application application) {
        ctx.close();
        super.onStop(application);
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader requestHeader, Throwable throwable) {
        Throwable th = throwable;
        while (th.getCause() != null) th = th.getCause();
        ObjectNode result = Json.newObject();
        result.put("message", th.getMessage());
        return F.Promise.<Result>pure(internalServerError(th.getMessage()));
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
        return new CorsAction(super.onRequest(request, actionMethod));
    }

    @Override
    public <A> A getControllerInstance(Class<A> aClass) throws Exception {
        return ctx.getBean(aClass);
    }

    @Configuration
    @EnableJpaRepositories
    public static class DatabaseConfiguration {

        @Bean
        public LocalEntityManagerFactoryBean entityManagerFactory() {
            LocalEntityManagerFactoryBean bean = new LocalEntityManagerFactoryBean();
            bean.setPersistenceUnitName(DEFAULT_PERSISTENCE_UNIT);
            bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
            return bean;
        }

        @Bean
        public HibernateExceptionTranslator hibernateExceptionTranslator() {
            return new HibernateExceptionTranslator();
        }

        @Bean
        public JpaTransactionManager transactionManager() {
            return new JpaTransactionManager();
        }

    }

}
