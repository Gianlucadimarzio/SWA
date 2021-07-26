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
import rest.RESTWebApplicationException;



public class CollezioneResource {
    private final Collezione c; 



    
    public CollezioneResource( Collezione c){
        this.c = c;
        if( !c.getPrivacy().equals("pubblica") ) {
            String m = "La collezione a cui sta cercando di accedere non è pubblica!";
            throw new RESTWebApplicationException( 401, m );
        }
    }
    
    //GET COLLEZIONI/<ID>
    @GET
    @Produces("application/json")
    public Response getCollezione( @Context UriInfo uribuilder ) throws RESTWebApplicationException {
        try {             
            List<Map<String, Object>> l = c.CollezioneDummy( uribuilder );
            return Response.ok(l).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
    }

    @Path("dischi")
    public DischiResource getDischi( @Context UriInfo uribuilder ){
        return new DischiResource(c, uribuilder);
    }  
        
    
    
}