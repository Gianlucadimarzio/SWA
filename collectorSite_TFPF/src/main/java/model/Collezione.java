package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import resources.CollezioneResource;
import resources.DiscoResource;


public class Collezione {

    private String titolo;
    private Utente utente;
    private List<Disco> dischi;
    private String privacy;

    public Collezione() {
        titolo = "";
        utente = new Utente();
        dischi = new ArrayList<>();
        privacy = "";

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    public List<Disco> getDischi() {
        return dischi;
    }

    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
    }
    
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setUtente(JsonNode get, Class<Utente> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Map<String, Object>> CollezioneDummy( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "titolo", this.getTitolo() );
        m.put( "creatore", this.getUtente().getUsername() );
        m.put( "privacy", this.getPrivacy() );
        List<String> urlDischi = new ArrayList();
        for( Disco disco: dischi ){
            URI uri = uribuilder.getBaseUriBuilder().path(DiscoResource.class).path( DiscoResource.class,"getDisco" ).build(disco.getTitolo());
            urlDischi.add( uri.toString()  );
        }
        m.put( "dischi", urlDischi );
        
        l.add(m);
        return l;
    
    }
    
}
