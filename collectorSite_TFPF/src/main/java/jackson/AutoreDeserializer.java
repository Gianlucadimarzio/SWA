package jackson;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.Autore;



public class AutoreDeserializer extends JsonDeserializer<Autore> {

    @Override
    public Autore deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Autore a = new Autore();       
        JsonNode node = p.getCodec().readTree(p);
        if (node.has("nome")) a.setNome( node.get("nome").toString() );  
        if (node.has("cognome")) a.setCognome( node.get("cognome").toString() ); 
        return a;
    }

    
}
