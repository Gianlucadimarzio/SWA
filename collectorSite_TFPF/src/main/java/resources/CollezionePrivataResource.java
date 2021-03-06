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
import javax.ws.rs.PUT;
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
import model.RepoError;
import model.Traccia;
import model.TracciaAPIDummy;
import model.TracciaRepoAPI;
import rest.RESTWebApplicationException;
import security.Logged;


public class CollezionePrivataResource {
    private final Collezione c; 
    private  final DiscoRepoAPI api = new DiscoAPIDummy();
    private static final TracciaRepoAPI apiTraccia = new TracciaAPIDummy();



    
    public CollezionePrivataResource( Collezione c){
        this.c = c;
        if( !c.getPrivacy().equals("privata") ) {
            String m = "La collezione a cui sta cercando di accedere non è privata!";
            throw new RESTWebApplicationException( 401, m );
        }
    }
    
    @POST
    @Path("dischi")
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
            uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezionePrivataResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), id );

        } catch (RepoError ex) {
            Logger.getLogger(CollezionePrivataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.created( uri ).entity(uri.toString()).build();
    }
    
    @PUT
    @Path("dischi/{disco: [0-9]+}")
    @Consumes("application/json")
    @Produces("application/json")
    @Logged
    public Response UpdateDisco( Disco d, @PathParam("disco") String idDisco, @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if ( !c.getUtente().getId().equals( idUser ) ){ return Response.status(Response.Status.UNAUTHORIZED).build(); }
        try {
            if( ! api.updateDisco(d, idDisco) ){
                return Response.serverError().entity("Dati del disco non validi!").build();
            }
        } catch (RepoError ex) {
                return Response.serverError().build();
        }
        return Response.noContent().entity("Modifica avvenuta con successo!").build(); 
    }
    
    
    //GET COLLEZIONI/<ID>
    @GET
    @Produces("application/json")
    @Logged
    public Response getCollezione( @Context SecurityContext sec, @Context UriInfo uribuilder ) throws RESTWebApplicationException {
        try {             
            String idUser = sec.getUserPrincipal().getName();
            if ( c.getUtente().getId().equals( idUser ) ){
                List<Map<String, Object>> l = c.CollezioneDummy( uribuilder );
                return Response.ok(l).build(); 
            }
            else { return Response.status(Response.Status.UNAUTHORIZED).build(); }
          
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
    }
    
    @GET
    @Produces("application/json")
    @Path("dischi")
    @Logged
    public Response getDischi( @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if ( !c.getUtente().getId().equals( idUser ) ){ return Response.status(Response.Status.UNAUTHORIZED).build(); }
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        for( Disco disco : this.c.getDischi() ){
            m = new HashMap();
            m.put("titolo", disco.getTitolo());
            
            URI uri = null;
            if( c.getPrivacy().equals("privata") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezionePrivataResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            if( c.getPrivacy().equals("pubblica") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezionePrivataResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            if( c.getPrivacy().equals("condivisa") ){
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa").path(CollezionePrivataResource.class, "getDischi").path(DischiResource.class, "getDisco").build( this.c.getId(), disco.getId() );
            }
            m.put("url", uri.toString());
            l.add( m );
        }
        return Response.ok(l).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("dischi/{idDisco:[0-9]+}")
    @Logged  
    public Response getDisco( @Context SecurityContext sec, @PathParam("idDisco") String idDisco, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if( !c.getUtente().getId().equals(idUser) ) { return Response.status(Response.Status.UNAUTHORIZED).build(); } 
        
        Disco d = new Disco();
        try {
            d = api.getDisco(idDisco);
            List<Map<String, Object>> l = d.DiscoDummy(this.c , uribuilder );
            if( d == null ) { return Response.status(Response.Status.NOT_FOUND).entity("Disco non trovato").build(); }
            return Response.ok(l).build();    
        } catch (RepoError ex) {
            Logger.getLogger(CollezionePrivataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(d).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("dischi/{idDisco:[0-9]+}/tracce")
    @Logged 
    public Response getTracce( @Context SecurityContext sec, @PathParam("idDisco") String idDisco, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if( !c.getUtente().getId().equals(idUser) ) { return Response.status(Response.Status.UNAUTHORIZED).build(); } 
        
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            Disco d = api.getDisco(idDisco);
            for( Traccia traccia : d.getTracce() ){
                URI uri = null;
                String idT = traccia.getId();
                m = new HashMap();
                m.put("titolo", traccia.getTitolo());
                uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezionePrivataResource.class, "getTraccia").build( this.c.getId(), d.getId(), traccia.getId() );
                m.put("url", uri.toString());
                l.add( m );
            }

        } catch (RepoError ex) {
            Logger.getLogger(CollezionePrivataResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(l).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("dischi/{idDisco:[0-9]+}/tracce/{ idTraccia: [0-9]+ }")
    @Logged
    public Response getTraccia( @Context SecurityContext sec, @PathParam("idTraccia") String id, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        if( !c.getUtente().getId().equals(idUser) ) { return Response.status(Response.Status.UNAUTHORIZED).build(); } 
        
        Traccia t = new Traccia();
        try {
            t = apiTraccia.getTraccia(id);
            if( t == null ) { return null; }
            return Response.ok(t).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        
    }
     
}