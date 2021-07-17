package resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.Disco;
import model.DiscoAPIDummy;
import model.DiscoRepoAPI;


import rest.RESTWebApplicationException;
import security.Logged;


public class CollezioneResource {
    private final Collezione c; 



    
    public CollezioneResource( Collezione c){
        this.c = c;

    }
    
    //GET COLLEZIONI/<ID>
    @GET
    @Produces("application/json")
    public Response getCollezione( @Context UriInfo uribuilder ) throws RESTWebApplicationException {
        try {             
            //String idUser = sec.getUserPrincipal().getName();
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