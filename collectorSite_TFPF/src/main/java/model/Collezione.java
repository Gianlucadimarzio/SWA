package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import resources.CollezioneCondivisaResource;
import resources.CollezioneResource;
import resources.CollezioniResource;
import resources.DischiResource;
import resources.DiscoResource;


public class Collezione {

    private String id;
    private String titolo;
    private Utente utente;
    private List<Disco> dischi;
    private String privacy;

    public Collezione() {
        id= "";
        titolo = "";
        utente = new Utente();
        dischi = new ArrayList<>();
        privacy = "";

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
    
    public List<Map<String, Object>> CollezioneDummy( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
          
        m.put( "titolo", this.getTitolo() );
        m.put( "creatore", this.getUtente().getUsername() );
        m.put( "privacy", this.getPrivacy() );
        List<String> urlDischi = new ArrayList();
        for( Disco disco: dischi ){
            URI uri = null;
            if( this.getPrivacy().equals("privata") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.getId(), disco.getId() );
            }
            if( this.getPrivacy().equals("pubblica") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.getId(), disco.getId() );
            }
            urlDischi.add( uri.toString() );
        }
        m.put( "dischi", urlDischi );
        
        l.add(m);
        return l;
    
    }
    
    public List<Map<String, Object>> CollezioneCondivisaDummy( UriInfo uribuilder, String utenteShare ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
          
        m.put( "titolo", this.getTitolo() );
        m.put( "creatore", this.getUtente().getUsername() );
        m.put( "privacy", this.getPrivacy() );
        List<String> urlDischi = new ArrayList();
        for( Disco disco: dischi ){
            URI uri = null;
            if( this.getPrivacy().equals("condivisa") ){                
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa" ).path(CollezioneCondivisaResource.class, "getDisco").build( utenteShare , this.getId(), disco.getId() );
            }
            urlDischi.add( uri.toString() );
        }
        m.put( "dischi", urlDischi );
        
        l.add(m);
        return l;
    
    }
    
}
