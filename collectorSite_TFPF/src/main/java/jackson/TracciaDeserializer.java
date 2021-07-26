package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.Traccia;


public class TracciaDeserializer extends JsonDeserializer<Traccia> {

    @Override
    public Traccia deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Traccia t = new Traccia();       
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("titolo")) t.setTitolo( node.get("titolo").toString() );  
        if (node.has("durata")) t.setDurata( node.get("durata").toString() );     
        return t;
    }
}
