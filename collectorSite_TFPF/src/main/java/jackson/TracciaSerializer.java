package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Traccia;

public class TracciaSerializer extends JsonSerializer<Traccia> {

    @Override
    public void serialize(Traccia item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeObjectField("titolo", item.getTitolo()); 
            jgen.writeObjectField("durata", item.getDurata());
        jgen.writeEndObject();
    }
}
