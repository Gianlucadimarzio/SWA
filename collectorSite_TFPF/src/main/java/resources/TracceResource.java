package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.Disco;
import model.Traccia;
import model.TracciaAPIDummy;
import model.TracciaRepoAPI;
import rest.RESTWebApplicationException;

public class TracceResource {
    private final Disco d;
    private final Collezione c;

    private UriInfo uribuilder;
    private static final TracciaRepoAPI api = new TracciaAPIDummy();


    public TracceResource( Collezione c, Disco d , UriInfo uribuilder ){
        this.c = c;
        this.d = d;
        this.uribuilder = uribuilder;
    }
    
    @GET
    @Produces("application/json")
    public Response getTracce( UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        for( Traccia traccia : d.getTracce() ){
            m = new HashMap();
            m.put("titolo", traccia.getTitolo());
            URI uri = this.uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").path(DiscoResource.class, "getTracce").path(TracceResource.class, "getTraccia").build( this.c.getId(), this.d.getId(), traccia.getId());
            m.put("url", uri.toString());
            l.add( m );
        }
        return Response.ok(l).build();
    }
    
    @Path("{ idTraccia: [0-9]+ }")
    public TracciaResource getTraccia( @PathParam("idTraccia") String id, @Context UriInfo uribuilder ){
        Traccia t = new Traccia();
        try {
            t = api.getTraccia(id);
            if( t == null ) { return null; }
            return new TracciaResource(t);           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        
    }
    
}
