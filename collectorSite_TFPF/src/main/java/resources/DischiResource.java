package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.ok;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.Disco;
import model.DiscoAPIDummy;
import model.DiscoRepoAPI;
import model.RepoError;
import rest.RESTWebApplicationException;
import security.Logged;

public class DischiResource {
    
    private final Collezione c;
    private UriInfo uribuilder;
    private static final DiscoRepoAPI api = new DiscoAPIDummy();

    
    public DischiResource( Collezione c , UriInfo uribuilder ){
        this.c = c;
        this.uribuilder = uribuilder;
    }
    
    @GET
    @Produces("application/json")
    public Response getDischi( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        for( Disco disco : this.c.getDischi() ){
            m = new HashMap();
            m.put("titolo", disco.getTitolo());
            
            URI uri = null;
            if( c.getPrivacy().equals("privata") ){
                uri = this.uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            if( c.getPrivacy().equals("pubblica") ){
                uri = this.uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            if( c.getPrivacy().equals("condivisa") ){
                uri = this.uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            m.put("url", uri.toString());
            l.add( m );
        }
        return Response.ok(l).build();
    }
    
    @Path("{ idDisco: [0-9]+ }")
    public DiscoResource getDisco( @PathParam("idDisco") String id, @Context UriInfo uribuilder ){
        Disco d = new Disco();
        try {
            
            d = api.getDisco(id);
 
            if( d == null ) { return null; }
            return new DiscoResource( this.c , d );           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Logged
    public Response InsertDisco( Disco d, @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if ( !c.getUtente().getId().equals( idUser ) ){ return Response.status(Response.Status.UNAUTHORIZED).build(); }
        URI uri = null;
        try {
            String id = api.insertDisco( this.c.getId(), d, idUser );            
            if( id == null ){
                return Response.serverError().entity("Dati del disco non validi!").build();
            }
            uri = this.uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), id );
        } catch (RepoError ex) {
            Logger.getLogger(CollezionePrivataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok( uri.toString() ).build();
    }
    
}
