package security;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@Logged
@Priority(Priorities.AUTHENTICATION)
public class LoggedFilter implements ContainerRequestFilter {

    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {        
        String token = null;
        final String path = requestContext.getUriInfo().getAbsolutePath().toString();
        
        //String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (requestContext.getCookies().containsKey("token")) {
            token = requestContext.getCookies().get("token").getValue();
        }
                              
        if (token != null && !token.isEmpty()) {
            try {             
                final String user = validateToken(token);
                if (user != null) {
                    requestContext.setProperty("token", token);
                    requestContext.setProperty("user", user);
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return new Principal() {
                                @Override
                                public String getName() {
                                    return user;
                                }
                            };
                        }
                        @Override
                        public boolean isUserInRole(String role) {
                            return true;
                        }
                        @Override
                        public boolean isSecure() {                            
                            return path.startsWith("https");
                        }
                        @Override
                        public String getAuthenticationScheme() {
                            return "Token-Based-Auth-Scheme";
                        }
                    });

                } else {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String validateToken(String token) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String query = "SELECT * FROM utente WHERE token = '" + token + "'";
            PreparedStatement pst = con.prepareStatement( query );
            ResultSet rs = pst.executeQuery();
            if( rs.next() ) { 
                return rs.getString("id"); 
            } 
            else { return ""; }
        } 
        catch (Exception ex) { return ""; }
    }

}
