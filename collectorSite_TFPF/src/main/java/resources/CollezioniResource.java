package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import model.Collezione;
import model.CollezioneAPIDummy;
import model.CollezioneCondivisa;
import model.CollezioneRepoAPI;
import model.RepoError;
import model.Utente;
import rest.RESTWebApplicationException;
import security.Logged;

@Path("v1/collezioni")
public class CollezioniResource {
    
    private static final CollezioneRepoAPI api = new CollezioneAPIDummy();
    
    @GET
    @Path("pubblica")
    @Produces("application/json")
    public Response getCollezioniPubbliche( @Context UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Collezione> results = null;
            results = api.getCollezioniPubbliche();
            
            for(Collezione result : results){
                m = new HashMap();
                m.put("titolo", result.getTitolo());
                URI uri = uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class,"getCollezionePubblica" ).build( result.getId());
                m.put("url", uri.toString());
                l.add( m );
            }

        }
        catch (RepoError ex) { return Response.serverError().build(); }
        return Response.ok(l).build();
          

    }
    
    @GET
    @Path("condivisa")
    @Produces("application/json")
    @Logged
    public Response getCollezioniCondivise( @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<CollezioneCondivisa> results = null;
            results = api.getCollezioniCondivise( idUser );
            
            for(CollezioneCondivisa result : results){                              
                URI uri = null;
                for( Utente u: result.getUtenteCondivisione() ){
                    m = new HashMap(); 
                    uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class ).path( CollezioniResource.class, "getCollezioneCondivisa" ).build( u.getId(), result.getCollezione().getId() );
                    m.put("titolo", result.getCollezione().getTitolo());
                    m.put("url", uri.toString());
                    m.put("utente condivisione", u.getUsername());
                    l.add( m );
                }

            }

        }
        catch (RepoError ex) { return Response.serverError().build(); }
        return Response.ok(l).build();
          
    }
    
    @GET
    @Path("condivisa/{utente : [0-9]+ }")
    @Produces("application/json")
    @Logged
    public Response getCollezioniCondivise( @Context SecurityContext sec, @PathParam("utente") String utenteShare, @Context UriInfo uribuilder ){
        if( utenteShare == null || utenteShare.isEmpty() ) return Response.serverError().build(); 
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Collezione> results = null;
            String idUser = sec.getUserPrincipal().getName();
            results = api.getCollezioniCondivise( idUser, utenteShare);
        
            for(Collezione result : results){
                m = new HashMap();
                m.put("titolo", result.getTitolo());
                URI uri = uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class,"getCollezioneCondivisa" ).build( utenteShare, result.getId());
                m.put("url", uri.toString());
                l.add( m );
            }

        }
        catch (Exception ex) { return Response.serverError().build(); }
        return Response.ok(l).build();
          
    }

    @GET
    @Path("privata")
    @Produces("application/json")
    @Logged
    public Response getCollezioniPrivate( @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m;
        try {
            List<Collezione> results = null;
            results = api.getCollezioniPrivate( idUser );
            
            for(Collezione result : results){
                m = new HashMap();
                m.put("titolo", result.getTitolo());
                URI uri = uribuilder.getBaseUriBuilder().path(CollezioniResource.class).path(CollezioniResource.class,"getCollezionePrivata" ).build( result.getId());
                m.put("url", uri.toString());
                l.add( m );
            }

        }
        catch (RepoError ex) { return Response.serverError().build(); }
        return Response.ok(l).build();
          
    }
    
    @Path("pubblica/{ idCollezione: [0-9]+ }")
    public CollezioneResource getCollezionePubblica( @PathParam("idCollezione") String id, @Context UriInfo uribuilder) throws RESTWebApplicationException {
              
        Collezione c = new Collezione();
        try {
            c = api.getCollezione(id);
            if( c == null ){ return null; }
            return new CollezioneResource(c);           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
       
    }
    
    @Path("privata/{ idCollezione: [0-9]+ }")
    public CollezionePrivataResource getCollezionePrivata( @PathParam("idCollezione") String id, @Context UriInfo uribuilder) throws RESTWebApplicationException {
        Collezione c = new Collezione();
        try {
            c = api.getCollezione(id);
            if( c == null ){ return null; }
            return new CollezionePrivataResource(c);           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        

    }
    
    @Logged
    @Path("condivisa/{ utente: [0-9]+ }/{ idCollezione: [0-9]+ }")
    public CollezioneCondivisaResource getCollezioneCondivisa(  @PathParam("utente") String utenteShare, @PathParam("idCollezione") String id, @Context UriInfo uribuilder) throws RESTWebApplicationException {
        
        Collezione c = new Collezione();
        try {
            c = api.getCollezione(id);
            if( c == null ){ return null; }
            return new CollezioneCondivisaResource(c, utenteShare);           
        }
        catch (Exception ex) { throw new RESTWebApplicationException(ex); }
        

    } 
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("privata/ricercaDisco")
    @Logged
    @Produces("application/json")
    public Response searchDiscoPrivato( @Context SecurityContext sec, @FormParam("nomeDisco") String nomeDisco, @FormParam("durataMaxDisco") String maxDurata, @FormParam("durataMinDisco") String minDurata, @Context UriInfo uribuilder ){
        if( nomeDisco == null || maxDurata == null || minDurata == null ) return Response.status(404).build();
        String idUser = sec.getUserPrincipal().getName();
        List<String> l = null;
        try {
            l = api.searchDiscoPrivato( nomeDisco, maxDurata, minDurata, idUser, uribuilder );
            if( l == null ) return Response.status(404).build();

        } catch (RepoError ex) {
            Logger.getLogger(CollezioniResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(l).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("pubblica/ricercaDisco")
    @Produces("application/json")
    public Response searchDiscoPubblico( @FormParam("nomeDisco") String nomeDisco, @FormParam("durataMaxDisco") String maxDurata, @FormParam("durataMinDisco") String minDurata, @Context UriInfo uribuilder ){
        if( nomeDisco == null || maxDurata == null || minDurata == null ) return Response.status(404).build();
        List<String> l = null;
        try {
            l = api.searchDiscoPubblico( nomeDisco, maxDurata, minDurata, uribuilder );
            if( l == null ) return Response.status(404).build();
        } catch (RepoError ex) {
            Logger.getLogger(CollezioniResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.ok(l).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("condivisa/ricercaDisco")
    @Logged
    @Produces("application/json")
    public Response searchDiscoCondiviso( @Context SecurityContext sec, @FormParam("nomeDisco") String nomeDisco, @FormParam("durataMaxDisco") String maxDurata, @FormParam("durataMinDisco") String minDurata, @Context UriInfo uribuilder ){
        if( nomeDisco == null || maxDurata == null || minDurata == null ) return Response.status(404).build();
        String idUser = sec.getUserPrincipal().getName();
        List<String> l = null;
        try {
            l = api.searchDiscoCondiviso( nomeDisco, maxDurata, minDurata, idUser, uribuilder );
            if( l == null ) return Response.status(404).build();

        } catch (RepoError ex) {
            Logger.getLogger(CollezioniResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return Response.ok(l).build();
    }
    

    
    @GET
    @Path("pubblica/statistiche")
    @Produces("application/json")
    public Response getStatistichePubbliche ( @Context UriInfo uribuilder ){
        List<Map<String, Object>> l = new ArrayList();
        try {
            l = api.getStatistichePubbliche();
            return Response.ok(l).build();

        }
        catch (RepoError ex) { return Response.serverError().build(); }  
          
    }
    
    @GET
    @Path("privata/statistiche")
    @Produces("application/json")
    @Logged
    public Response getStatistichePrivate ( @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        List<Map<String, Object>> l = new ArrayList();
        try {
            l = api.getStatistichePrivate( idUser );
            return Response.ok(l).build();

        }
        catch (RepoError ex) { return Response.serverError().build(); }  
          
    }
    
    @GET
    @Path("condivisa/statistiche")
    @Produces("application/json")
    @Logged
    public Response getStatisticheCondivise ( @Context SecurityContext sec, @Context UriInfo uribuilder ){
        String idUser = sec.getUserPrincipal().getName();
        List<Map<String, Object>> l = new ArrayList();
        try {
            l = api.getStatisticheCondivise( idUser );
            return Response.ok(l).build();

        }
        catch (RepoError ex) { return Response.serverError().build(); }  
          
    }

}
