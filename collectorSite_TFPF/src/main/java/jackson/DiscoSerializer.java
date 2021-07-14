package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Disco;

public class DiscoSerializer extends JsonSerializer<Disco> {

    @Override
    public void serialize(Disco item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeObjectField("titolo", item.getTitolo()); 
            jgen.writeObjectField("autore", item.getAutore());
            jgen.writeObjectField("tracce", item.getTracce());
        jgen.writeEndObject();
    }
}
