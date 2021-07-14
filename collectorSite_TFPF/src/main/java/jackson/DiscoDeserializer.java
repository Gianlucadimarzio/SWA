package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import model.Autore;
import model.Disco;
import model.Traccia;


public class DiscoDeserializer extends JsonDeserializer<Disco> {

    @Override
    public Disco deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Disco d = new Disco();       
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("titolo")) d.setTitolo( node.get("titolo").toString() );  
        if (node.has("autore")) d.setAutore( node.get("autore"), Autore.class ); 
        if (node.has("tracce")) {
            JsonNode ne = node.get("tracce");
            List<Traccia> tracce = new ArrayList<>();
            d.setTracce( tracce );
            for (int i = 0; i < ne.size(); ++i) {
                tracce.add(jp.getCodec().treeToValue(ne.get(i), Traccia.class));
            }           
        }      
        return d;
    }
}
