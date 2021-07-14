package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.CollezioneAPIDummy;
import model.CollezioneRepoAPI;
import model.RepoError;
import model.Disco;
import model.Utente;

@Path("dischi")
public class AutoreResource {
    private static final CollezioneRepoAPI api = new CollezioneAPIDummy();
    
    @GET
    @Produces("application/json")
    @Path("{ titolo: [a-zA-Z0-9]+ }")
    public Response getAutori( @Context UriInfo uribuilder ){
        List<String> l = new ArrayList();
        try {
            List<Collezione> results = api.getCollezioni();
            for(Collezione result : results){
                URI uri = uribuilder.getBaseUriBuilder().path(CollezioneResource.class).path( CollezioneResource.class,"getCollezione" ).build(result.getTitolo());
                l.add( uri.toString() );
            }

        } catch (RepoError ex) {
            return Response.serverError().build();
        }
        return Response.ok(l).build();
          

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
