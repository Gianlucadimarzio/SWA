package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Autore;
import model.AutoreAPIDummy;
import model.AutoreRepoAPI;
import model.Disco;
import model.RepoError;
import model.Traccia;
import rest.RESTWebApplicationException;


@Path("v1/autori")
public class AutoriResource {
    private static final AutoreRepoAPI api = new AutoreAPIDummy();
    
    @GET
    @Produces("application/json")
    public Response getAutori( @Context UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Autore> results = api.getAutori();
            for(Autore result : results){
                m = new HashMap();
                m.put( "autore" , result.getNome()+ " "+ result.getCognome());
                URI uri = uribuilder.getBaseUriBuilder().path(AutoriResource.class).path(AutoriResource.class, "getAutore").build( result.getId() );
                m.put( "url" , uri.toString() );
                l.add( m );
            }

        } catch (RepoError ex) {
            return Response.serverError().build();
        }
        return Response.ok(l).build();   
    }

    @GET
    @Produces("application/json")
    @Path("{autore: [a-zA-z0-9]+}")
    public Response getAutore( @PathParam("autore") String idAutore, @Context UriInfo uribuilder ){
        Autore a = new Autore();
        try {
            a = api.getAutore(idAutore);
            if( a == null ) { return Response.status(Response.Status.NOT_FOUND).entity("Autore non trovato").build(); }
            return Response.ok(a).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
    }
    
    @GET
    @Produces("application/json")
    @Path("{autore: [a-zA-z0-9]+}/dischi")
    public Response getDischi( @PathParam("autore") String idAutore, @Context UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Disco> dischi = api.getDischi( idAutore );
            for( Disco disco : dischi ){
                m = new HashMap();
                m.put("titolo", disco.getTitolo());
                URI uri = null;
                uri = uribuilder.getBaseUriBuilder().path(AutoriResource.class).path(AutoriResource.class, "getDisco").build( idAutore, disco.getId() );
                m.put("url", uri.toString());
                l.add( m );
            }
        } catch (RepoError ex) { Logger.getLogger(AutoriResource.class.getName()).log(Level.SEVERE, null, ex); }

        return Response.ok( l ).build();
    } 
    
    @GET
    @Produces("application/json")
    @Path("{autore: [a-zA-z0-9]+}/dischi/{disco: [0-9]+}")
    public Response getDisco( @PathParam("autore") String idAutore, @PathParam("disco") String idDisco, @Context UriInfo uribuilder ){
        Disco d = new Disco();
        try {
            d = api.getDisco( idDisco );
            if( d == null ) { return Response.status(Response.Status.NOT_FOUND).entity("Autore non trovato").build(); }
            List<Map<String, Object>> l = d.DiscoAutoreDummy( uribuilder );
            return Response.ok( l ).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
    }
    
    @GET
    @Produces("application/json")
    @Path("{autore: [a-zA-z0-9]+}/dischi/{disco: [0-9]+}/tracce")
    public Response getTracce( @PathParam("autore") String idAutore, @PathParam("disco") String idDisco, @Context UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Traccia> tracce = api.getTracce( idDisco );
            for( Traccia traccia : tracce ){
                m = new HashMap();
                m.put("titolo", traccia.getTitolo());
                URI uri = null;
                uri = uribuilder.getBaseUriBuilder().path(AutoriResource.class).path(AutoriResource.class, "getTraccia").build( idAutore, idDisco, traccia.getId() );
                m.put("url", uri.toString());
                l.add( m );
            }
        } catch (RepoError ex) { Logger.getLogger(AutoriResource.class.getName()).log(Level.SEVERE, null, ex); }

        return Response.ok( l ).build();
    } 
    
    @GET
    @Produces("application/json")
    @Path("{autore: [a-zA-z0-9]+}/dischi/{disco: [0-9]+}/tracce/{traccia: [0-9]+}")
    public Response getTraccia( @PathParam("autore") String idAutore, @PathParam("disco") String idDisco, @PathParam("traccia") String idTraccia, @Context UriInfo uribuilder ){
        Traccia t = new Traccia();
        try {
            t = api.getTraccia( idTraccia );
        } catch (RepoError ex) { Logger.getLogger(AutoriResource.class.getName()).log(Level.SEVERE, null, ex); }

        return Response.ok( t ).build();
    } 
    
}


