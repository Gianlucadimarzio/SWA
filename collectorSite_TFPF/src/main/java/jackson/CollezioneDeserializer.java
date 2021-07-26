package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import model.Collezione;
import model.Disco;
import model.Utente;


public class CollezioneDeserializer extends JsonDeserializer<Collezione> {

    @Override
    public Collezione deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Collezione c = new Collezione();       
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("titolo")) c.setTitolo( node.get("titolo").toString() );  
        if( node.has("utente"))   c.setUtente(jp.getCodec().treeToValue(node.get("utente"), Utente.class));
        if (node.has("privacy")) c.setPrivacy( node.get("privacy").toString() ); 
        if (node.has("dischi")) {
            JsonNode ne = node.get("dischi");
            List<Disco> dischi = new ArrayList<>();
            c.setDischi( dischi );
            for (int i = 0; i < ne.size(); ++i) {
                dischi.add(jp.getCodec().treeToValue(ne.get(i), Disco.class));
            }           
        }      
        return c;
    }
}
