
package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import model.Collezione;
import model.Disco;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
        this.mapper = createObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule customSerializer = new SimpleModule("CustomSerializersModule");


        customSerializer.addSerializer(Collezione.class, new CollezioneSerializer());
        customSerializer.addDeserializer(Collezione.class, new CollezioneDeserializer());
        
        customSerializer.addSerializer(Disco.class, new DiscoSerializer());
        customSerializer.addDeserializer(Disco.class, new DiscoDeserializer());

        mapper.registerModule(customSerializer);

        return mapper;
    }
}
