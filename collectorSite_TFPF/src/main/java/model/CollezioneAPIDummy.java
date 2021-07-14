package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CollezioneAPIDummy implements CollezioneRepoAPI {    

    @Override
    public List<Collezione> getCollezioni() throws RepoError {
        List<Collezione> collezioni = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setTitolo(rs.getString("titolo"));
                collezioni.add( collezione );
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return collezioni;
    }

    @Override
    public Collezione getCollezione(String titolo) throws RepoError { 
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE titolo = '" + titolo + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                Collezione collezione = new Collezione();
                
                collezione.setTitolo( rs.getString( "titolo" ) );  
                collezione.setPrivacy( rs.getString( "privacy" ) ); 
                
                String sqlUtente = "SELECT * FROM utente WHERE id =" + rs.getString("id_utente");
                PreparedStatement pstUtente = con.prepareStatement(sqlUtente);
                ResultSet rsUtente = pstUtente.executeQuery();                
                Utente u = new Utente(); 
                if( rsUtente.next() ){
                    u.setUsername( rsUtente.getString("username") );
                    collezione.setUtente(u);
                }
                else { return null; }
                String sqlDischi = "SELECT * FROM dischi_collezione dc JOIN disco d ON (d.id=dc.id_disco) WHERE dc.id_collezione = " +rs.getString("id");
                PreparedStatement pstDischi = con.prepareStatement(sqlDischi);
                ResultSet rsDischi = pstDischi.executeQuery();
                List<Disco> dischi = new ArrayList();
                while( rsDischi.next() ){
                    Disco disco = new Disco();
                    disco.setTitolo(rsDischi.getString("titolo"));
                    dischi.add(disco);
                }
                collezione.setDischi(dischi);
                con.close();
                return collezione;
            }
            else { return null; }           
        }
        catch (SQLException ex) { return null; }
        catch (ClassNotFoundException ex) { return null; }
    }

    @Override
    public boolean addDiscoToCollezione(String titolo, Disco disco) throws RepoError {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE titolo = '" + titolo + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                String id = rs.getString("id");
                
                String sqlDisco = "SELECT * FROM disco WHERE titolo = '" + disco.getTitolo() + "'";
                PreparedStatement pstDisco = con.prepareStatement(sqlDisco);
                ResultSet rsDisco = pstDisco.executeQuery();
                if( rsDisco.next() ){
                    String sqlInsert = "INSERT INTO dischi_collezione(id_collezione, id_disco) VALUES (" + id + "," + rsDisco.getString("id") + ")";
                    PreparedStatement pstInsert = con.prepareStatement(sqlInsert);
                    ResultSet rsInsert = pstInsert.executeQuery(); 
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }
    
    
    
}
