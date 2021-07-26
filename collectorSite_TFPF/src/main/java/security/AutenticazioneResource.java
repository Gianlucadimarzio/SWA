package security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("v1")
public class AutenticazioneResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doLogin(@Context UriInfo uriinfo,
            @FormParam("username") String username,
            @FormParam("password") String password) {
        try {
            if (authenticate(username, password)) {
                String authToken = issueToken(uriinfo, username);
                
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
                String query = "UPDATE utente SET token = '" + authToken + "' WHERE username ='" + username + "'";
                PreparedStatement pst = con.prepareStatement( query );
                pst.executeUpdate();    
                
                return Response.ok("LOGIN EFFETTUATO").cookie(new NewCookie("token", authToken)).header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).build();
            }
            else { return Response.status(Response.Status.UNAUTHORIZED).entity("CREDENZIALI NON VALIDE").build(); }
        }
        catch (Exception e) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    }

    @Logged
    @DELETE
    @Path("/logout")
    public Response doLogout(@Context HttpServletRequest request) {
        try {
            String token = (String) request.getAttribute("token");
            if (token != null) {
                revokeToken(token);
            }
            return Response.ok("LOGOUT EFFETTUATO").cookie(new NewCookie("token", null)).build();

        }
        catch (Exception e) { return Response.serverError().build(); }
    }

    private boolean authenticate(String username, String password) {    
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String query = "SELECT * FROM utente WHERE username = '" + username + "' AND password = '" + password + "'";
            PreparedStatement pst = con.prepareStatement( query );
            ResultSet rs = pst.executeQuery();
            if( rs.next() ) { 
                return true; 
            } 
            else { return false; }
        } 
        catch (SQLException ex) { return false; }
        catch (ClassNotFoundException ex) { return false; } 
    }

    private String issueToken(UriInfo context, String username) {
        String token = username + UUID.randomUUID().toString();
        return token;
    }

    private void revokeToken( String token ) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String query = "UPDATE utente SET token = NULL WHERE token ='" + token + "'";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate(); 
        }
        catch (Exception ex) {
            Logger.getLogger(AutenticazioneResource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
