package rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import jackson.ObjectMapperContextResolver;
import resources.AutoriResource;
import resources.CollezioneResource;
import resources.CollezioniResource;
import resources.DischiResource;
import resources.DiscoResource;
import resources.TracceResource;
import resources.TracciaResource;
import security.AutenticazioneResource;
import security.LoggedFilter;

/**
 *
 * @author didattica
 */
@ApplicationPath("rest")
public class RESTApp extends Application {

    private final Set<Class<?>> classes;

    public RESTApp() {
        HashSet<Class<?>> c = new HashSet<>();
       
        c.add(CollezioniResource.class);
        c.add(CollezioneResource.class);
        c.add(AutoriResource.class);
        //c.add(AutoreResource.class);
        c.add(DischiResource.class);
        c.add(DiscoResource.class);
        c.add(TracceResource.class);
        c.add(TracciaResource.class);

        c.add(AutenticazioneResource.class);

        c.add(JacksonJsonProvider.class);

        c.add(ObjectMapperContextResolver.class);

        //esempio di autenticazione
        c.add(LoggedFilter.class);

        //aggiungiamo il filtro che gestisce gli header CORS
        //c.add(CORSFilter.class);

        //esempio di exception mapper, che mappa in Response eccezioni non già derivanti da WebApplicationException
        //c.add(AppExceptionMapper.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
