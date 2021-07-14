package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DiscoAPIDummy implements DiscoRepoAPI {    

    @Override
    public List<Disco> getDischi() throws RepoError {
        List<Disco> dischi = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM disco";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Disco disco = new Disco();
                disco.setTitolo(rs.getString("titolo"));
                dischi.add( disco );
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(DiscoAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return dischi; 
    }

    @Override
    public Disco getDisco(String titolo) throws RepoError {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM disco d JOIN autore a ON (d.id_autore = a.id) WHERE titolo = '" + titolo + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                Disco disco = new Disco();
                
                disco.setTitolo( rs.getString( "titolo" ) );  
                Autore a = new Autore();
                a.setNome( rs.getString("nome") );
                a.setCognome( rs.getString("cognome") );
                disco.setAutore(a);
                String sqlTracce = "SELECT * FROM tracce_disco td JOIN traccia t ON ( td.id_traccia = t.id ) WHERE td.id_disco = " +rs.getString("id");
                PreparedStatement pstTracce = con.prepareStatement(sqlTracce);
                ResultSet rsTracce = pstTracce.executeQuery();
                List<Traccia> tracce = new ArrayList();
                while( rsTracce.next() ){
                    Traccia traccia = new Traccia();
                    traccia.setTitolo(rsTracce.getString("titolo"));
                    traccia.setDurata(rsTracce.getInt("durata"));
                    tracce.add(traccia);
                }
                disco.setTracce(tracce);
                con.close();
                return disco;
            }
            else { return null; }           
        }
        catch (SQLException ex) { return null; }
        catch (ClassNotFoundException ex) { return null; }    }
    
}
