package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import resources.DiscoResource;
import resources.TracciaResource;

public class Disco {

    private String titolo;
    private Autore autore;
    private List<Traccia> tracce;


    public Disco() {
        titolo = "";
        autore = new Autore();
        tracce = new ArrayList<Traccia>();

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }
    
    public List<Traccia> getTracce() {
        return tracce;
    }

    public void setTracce(List<Traccia> tracce) {
        this.tracce = tracce;
    }

    public void setAutore(JsonNode get, Class<Autore> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Map<String, Object>> DiscoDummy( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "titolo", this.getTitolo() );
        m.put( "autore", this.getAutore().getNome() + " " + this.getAutore().getCognome() );
        List<String> urlTracce = new ArrayList();
        for( Traccia traccia : tracce ){
            URI uri = uribuilder.getBaseUriBuilder().path(TracciaResource.class).path( TracciaResource.class,"getTraccia" ).build(traccia.getTitolo());
            urlTracce.add( uri.toString()  );
        }
        m.put( "tracce", urlTracce );

        l.add(m);
        return l;
    
    }
    
}
