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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.CollezioneAPIDummy;
import model.CollezioneRepoAPI;
import model.Disco;
import model.DiscoAPIDummy;
import model.DiscoRepoAPI;
import model.RepoError;
import model.Traccia;
import model.TracciaAPIDummy;
import model.TracciaRepoAPI;


import rest.RESTWebApplicationException;
import security.Logged;


public class CollezioneCondivisaResource {
    private final Collezione c; 
    private String utenteShare;
    private  final DiscoRepoAPI api = new DiscoAPIDummy();
    private static final TracciaRepoAPI apiTraccia = new TracciaAPIDummy();



    
    public CollezioneCondivisaResource( Collezione c, String utenteShare ){
        this.c = c;
        this.utenteShare = utenteShare;
        if( !c.getPrivacy().equals("condivisa") ) {
            throw new RESTWebApplicationException( 404, "La collezione a cui sta cercando di accedere non è condivisa!" );
        }
    }

    
    //GET COLLEZIONI/CONDIVISA/<UTENTE>/<ID>
    @GET
    @Produces("application/json")
    @Logged
    public Response getCollezione( @Context SecurityContext sec, @Context UriInfo uribuilder ) throws RESTWebApplicationException {
        CollezioneRepoAPI ca = new CollezioneAPIDummy(); 
        List<Map<String, Object>> l = c.CollezioneCondivisaDummy( uribuilder, this.utenteShare );
        try {             
            String idUser = sec.getUserPrincipal().getName();
            
            if ( ( c.getUtente().getId().equals( idUser ) && idUser.equals( this.utenteShare ) ) || ca.checkUtenteShare( c.getId() , this.utenteShare ) ){
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
            uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa" ).path(CollezioneCondivisaResource.class, "getDisco").build( this.utenteShare , c.getId(), disco.getId() );
            m.put("url", uri.toString());
            l.add( m );
        }
        CollezioneRepoAPI ca = new CollezioneAPIDummy(); 
        try {
            if ( !( ( c.getUtente().getId().equals( idUser ) && idUser.equals( this.utenteShare ) ) || ca.checkUtenteShare( c.getId() , this.utenteShare ) ) ){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            } 
        } catch (RepoError ex) {
            Logger.getLogger(CollezioneCondivisaResource.class.getName()).log(Level.SEVERE, null, ex);
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
            List<Map<String, Object>> l = d.DiscoCondivisoDummy( this.utenteShare, this.c , uribuilder );
            if( d == null ) { return Response.status(Response.Status.NOT_FOUND).entity("Disco non trovato").build(); }
                CollezioneRepoAPI ca = new CollezioneAPIDummy(); 
                if ( !( ( c.getUtente().getId().equals( idUser ) && idUser.equals( this.utenteShare ) ) || ca.checkUtenteShare( c.getId() , this.utenteShare ) ) ){
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            
            return Response.ok(l).build();    
        } catch (RepoError ex) {
            Logger.getLogger(CollezioneCondivisaResource.class.getName()).log(Level.SEVERE, null, ex);
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
                uri = uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class, "getCollezioneCondivisa").path(CollezioneCondivisaResource.class, "getTraccia").build( this.utenteShare , this.c.getId(), d.getId(), traccia.getId() );
                m.put("url", uri.toString());
                l.add( m );
            }
            CollezioneRepoAPI ca = new CollezioneAPIDummy(); 
            if ( !( ( c.getUtente().getId().equals( idUser ) && idUser.equals( this.utenteShare ) ) || ca.checkUtenteShare( c.getId() , this.utenteShare ) ) ){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (RepoError ex) {
            Logger.getLogger(CollezioneCondivisaResource.class.getName()).log(Level.SEVERE, null, ex);
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
            CollezioneRepoAPI ca = new CollezioneAPIDummy(); 
            if ( !( ( c.getUtente().getId().equals( idUser ) && idUser.equals( this.utenteShare ) ) || ca.checkUtenteShare( c.getId() , this.utenteShare ) ) ){
                    return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.ok(t).build();           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        
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
            uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa" ).path(CollezioneCondivisaResource.class, "getDisco").build( this.utenteShare , c.getId(), id );

        } catch (RepoError ex) {
            Logger.getLogger(CollezionePrivataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok( uri.toString() ).build();
    }
    
    
    
}