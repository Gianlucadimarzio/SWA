package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Collezione;

public class CollezioneSerializer extends JsonSerializer<Collezione> {

    @Override
    public void serialize(Collezione item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeObjectField("titolo", item.getTitolo()); 
            jgen.writeObjectField("utente", item.getUtente());
            jgen.writeObjectField("privacy", item.getPrivacy());
            jgen.writeObjectField("dischi", item.getDischi());
        jgen.writeEndObject();
    }
}
