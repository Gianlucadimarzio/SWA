package resources;

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
import model.DiscoAPIDummy;
import model.DiscoRepoAPI;
import rest.RESTWebApplicationException;

public class DiscoResource {
    private final Disco d;
    private final Collezione c;

    public DiscoResource( Collezione c, Disco d) {
        this.d = d;
        this.c = c;
    }
    
    @GET
    @Produces("application/json")
    public Response getDisco( @Context UriInfo uribuilder ){
        try {
            List<Map<String, Object>> l = d.DiscoDummy(this.c , uribuilder );
            if( d == null ) { return Response.status(Response.Status.NOT_FOUND).entity("Disco non trovato").build(); }
            return Response.ok(l).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        
    }
    
    @Path("tracce")
    public TracceResource getTracce( @Context UriInfo uribuilder ){
        return new TracceResource( this.c, this.d, uribuilder );
    }  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    @GET
    @Produces("application/json")
    public List<Map<String, Object>> test1( @QueryParam("nome") String nome ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        if( nome != null ){
            m.put( "nome", nome );
        }
        else{
            m.put( "nome", "Stecina" );
        }
        l.add( m );
        return l;
    }
    
    @GET
    @Produces("application/json")
    @Path( "{anno: [1-9][0-9][0-9][0-9]}" )
    public List<Map<String, Object>> test2( @PathParam("anno") int anno ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        m.put( "anno", anno );     
        l.add( m );
        return l;
    }
    uribuilder.getBaseUriBuilder().path( CollezioneResource.class ).path( CollezioneResource.class,"test2" ); // prenmde il path collezioni/anno
    */
}
