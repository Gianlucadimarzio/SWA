package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import model.Traccia;
import rest.RESTWebApplicationException;

public class TracciaResource {
    private final Traccia t;
    
    public TracciaResource( Traccia t ){
        this.t = t;
    }

    
    @GET
    @Produces("application/json")
    public Response getTraccia() {
        try{
            return Response.ok(t).build();    
        }
        catch (Exception ex) {  throw new RESTWebApplicationException(ex); }

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
