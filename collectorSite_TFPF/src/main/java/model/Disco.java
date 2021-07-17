package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import resources.AutoriResource;
import resources.CollezioneCondivisaResource;
import resources.CollezioneResource;
import resources.CollezioniResource;
import resources.DischiResource;
import resources.DiscoResource;
import resources.TracceResource;
import resources.TracciaResource;

public class Disco {

    private String id;
    private String titolo;
    private Autore autore;
    private List<Traccia> tracce;


    public Disco() {
        id = "";
        titolo = "";
        autore = new Autore();
        tracce = new ArrayList<Traccia>();

    }
    
    public String getId() {
        return id;
    }
    
    public void setId( String id ){
        this.id = id;
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

    
    public List<Map<String, Object>> DiscoAutoreDummy( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "titolo", this.getTitolo() );
        m.put( "autore", this.getAutore().getId() );
        List<String> urlTracce = new ArrayList();
        for( Traccia traccia : tracce ){
            URI uri = null;
            uri = uribuilder.getBaseUriBuilder().path(AutoriResource.class).path(AutoriResource.class, "getTraccia").build( this.getAutore().getId(), this.id, traccia.getId() );
            urlTracce.add(uri.toString());        
        }
        m.put( "tracce", urlTracce );
        l.add(m);
        return l;
    
    }
    
    public List<Map<String, Object>> DiscoDummy( Collezione c, UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "titolo", this.getTitolo() );
        m.put( "autore", this.getAutore().getNome() + " " + this.getAutore().getCognome() );
        List<String> urlTracce = new ArrayList();
        for( Traccia traccia : tracce ){
            URI uri = null;
            if( c.getPrivacy().equals("privata") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").path(DiscoResource.class, "getTracce").path(TracceResource.class, "getTraccia").build( c.getId(), this.getId(), traccia.getId());
            }
            if( c.getPrivacy().equals("pubblica") ){
                
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").path(DiscoResource.class, "getTracce").path(TracceResource.class, "getTraccia").build( c.getId(), this.getId(), traccia.getId());
            }
            urlTracce.add(uri.toString());        

        }
        m.put( "tracce", urlTracce );
        l.add(m);
        return l;
    
    }
    
    public List<Map<String, Object>> DiscoCondivisoDummy( String utenteShare, Collezione c, UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "titolo", this.getTitolo() );
        m.put( "autore", this.getAutore().getNome() + " " + this.getAutore().getCognome() );
        List<String> urlTracce = new ArrayList();
        for( Traccia traccia : tracce ){
            URI uri = null;
            uri = uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class, "getCollezioneCondivisa").path(CollezioneCondivisaResource.class, "getTraccia").build( utenteShare , c.getId(), this.getId(), traccia.getId() );
            urlTracce.add(uri.toString());        

        }
        m.put( "tracce", urlTracce );
        l.add(m);
        return l;
    
    }
}
