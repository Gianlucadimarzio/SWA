package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Autore;

public class AutoreSerializer extends JsonSerializer<Autore> {

    @Override
    public void serialize(Autore item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeObjectField("nome", item.getNome()); 
            jgen.writeObjectField("cognome", item.getCognome());
        jgen.writeEndObject();
    }
}
