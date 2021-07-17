package model;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.UriInfo;
import resources.CollezioneResource;
import resources.CollezioniResource;
import resources.DiscoResource;


public class AutoreAPIDummy implements AutoreRepoAPI {    

    @Override
    public List<Autore> getAutori() throws RepoError {
        List<Autore> autori = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM autore";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Autore autore = new Autore();
                autore.setId(rs.getString("id"));
                autore.setNome(rs.getString("nome"));
                autore.setCognome(rs.getString("cognome"));
                autori.add( autore );
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autori;
    }
    
    
    @Override
    public Autore getAutore( String id ) throws RepoError {
        Autore autore = new Autore();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM autore WHERE id = " + id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if( rs.next() ){
                autore.setId(rs.getString("id"));
                autore.setNome(rs.getString("nome"));
                autore.setCognome(rs.getString("cognome"));
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autore;
    }
    
    @Override
    public List<Disco> getDischi( String id ) throws RepoError {
        List<Disco> dischi = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT d.id as idD, d.titolo as titoloD FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy = 'pubblica' AND d.id_autore = " + id ;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Disco disco = new Disco();
                disco.setId(rs.getString("idD"));
                disco.setTitolo(rs.getString("titoloD"));
                dischi.add( disco );
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dischi;
    }
    
    @Override
    public Disco getDisco( String idDisco ) throws RepoError {
        Disco d = new Disco();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT d.id as idD, d.titolo as titoloD, td.id_traccia as idT, a.id as idA, nome, cognome FROM disco d JOIN tracce_disco td ON (d.id = td.id_disco) JOIN autore a ON (d.id_autore = a.id) WHERE  d.id = " + idDisco ;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            List<Traccia> tracce = new ArrayList();
            if(rs.next()){
                Autore a = new Autore();
                a.setId( rs.getString("idA") );
                a.setNome( rs.getString("nome") );
                a.setCognome( rs.getString("cognome") );
                d.setId(rs.getString("idD"));
                d.setTitolo(rs.getString("titoloD"));
                d.setAutore( a );
                String sqlTracce = "SELECT * FROM traccia WHERE  id = " + rs.getString("idT") ;
                PreparedStatement pstTracce = con.prepareStatement(sqlTracce);
                ResultSet rsTracce = pstTracce.executeQuery();
                while ( rsTracce.next() ){
                    Traccia t = new Traccia();
                    t.setId(rsTracce.getString("id"));
                    t.setTitolo(rsTracce.getString("titolo"));
                    tracce.add( t );
                    d.setTracce( tracce );
                }
            }
            
            con.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
    @Override
    public List<Traccia> getTracce( String id ) throws RepoError {
        List<Traccia> tracce = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT td.id_traccia as idT, t.titolo as titoloT FROM tracce_disco td JOIN traccia t ON (td.id_traccia = t.id) WHERE  td.id_disco = " + id ;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Traccia traccia = new Traccia();
                traccia.setId(rs.getString("idT"));
                traccia.setTitolo(rs.getString("titoloT"));
                tracce.add( traccia );
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tracce;
    }
    
    @Override
    public Traccia getTraccia( String id ) throws RepoError {
        Traccia t = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            String sql = "SELECT * FROM traccia WHERE id = " + id ;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if( rs.next() ){
                t = new Traccia();
                t.setId(rs.getString("id"));
                t.setTitolo(rs.getString("titolo"));
                t.setDurata(rs.getString("durata"));
            }
            con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AutoreAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AutoreAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    
}
