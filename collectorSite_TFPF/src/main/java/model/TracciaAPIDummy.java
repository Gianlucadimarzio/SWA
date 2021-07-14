package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TracciaAPIDummy implements TracciaRepoAPI {    
    
    @Override
    public Traccia getTraccia(String titolo) throws RepoError {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM traccia WHERE titolo = '" + titolo + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                Traccia traccia = new Traccia();
                
                traccia.setTitolo( rs.getString( "titolo" ) ); 
                traccia.setDurata( rs.getInt( "durata" ) ); 
                con.close();
                return traccia;
            }
            else { return null; }           
        }
        catch (SQLException ex) { return null; }
        catch (ClassNotFoundException ex) { return null; }    }
    
}