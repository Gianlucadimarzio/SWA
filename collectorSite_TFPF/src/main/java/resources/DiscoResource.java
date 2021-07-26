package resources;

import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.Disco;
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

}
