package model;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriInfo;
import resources.CollezioneResource;
import resources.DiscoResource;


public class DiscoAPIDummy implements DiscoRepoAPI {    
    
    @Override
    public Disco getDisco(String id) throws RepoError {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT d.id as idDisco, titolo, nome, cognome, a.id as idAutore FROM disco d JOIN autore a ON (d.id_autore = a.id) WHERE d.id = " + id ;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            if ( rs.next() ){
                Disco disco = new Disco();
                disco.setId( rs.getString( "idDisco" ) );  
                disco.setTitolo( rs.getString( "titolo" ) );  
                Autore a = new Autore();
                a.setId( rs.getString("idAutore") );
                a.setNome( rs.getString("nome") );
                a.setCognome( rs.getString("cognome") );
                disco.setAutore(a);
                String sqlTracce = "SELECT t.id as idT, t.titolo as titoloT, t.durata as durataT FROM tracce_disco td JOIN traccia t ON ( td.id_traccia = t.id ) WHERE td.id_disco = " +rs.getString("idDisco");
                PreparedStatement pstTracce = con.prepareStatement(sqlTracce);
                ResultSet rsTracce = pstTracce.executeQuery();
                List<Traccia> tracce = new ArrayList();
                while( rsTracce.next() ){
                    Traccia traccia = new Traccia();
                    traccia.setId(rsTracce.getString("idT"));
                    traccia.setTitolo(rsTracce.getString("titoloT"));
                    traccia.setDurata(rsTracce.getString("durataT"));
                    tracce.add(traccia);
                }
                disco.setTracce(tracce);
                con.close();
                return disco;
            }
            else { return null; }   
        }
        catch (SQLException ex) { return null; }
        catch (ClassNotFoundException ex) { return null; }    
    }

    @Override
    public List<String> getDischiByCollezione(Collezione c , UriInfo uribuilder) throws RepoError {
        List<String> l = new ArrayList();
        for( Disco d : c.getDischi() ){
            URI uri;
            uri = uribuilder.getBaseUriBuilder().path(CollezioneResource.class, "getCollezione").path( DiscoResource.class,"getDisco" ).build(d.getId());
            l.add(uri.toString());
        }
        return l;
    }
    
    @Override
    public boolean updateDisco( Disco disco, String idDisco ) throws RepoError {
        String sqlUpdateDisco = null;
        PreparedStatement pstUpdateDisco = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            if( !( disco.getTitolo() == null || disco.getTitolo().isEmpty() )  ){
                sqlUpdateDisco = "UPDATE disco SET titolo = "+ disco.getTitolo() +" WHERE id = " + idDisco;
                pstUpdateDisco = con.prepareStatement(sqlUpdateDisco);
                pstUpdateDisco.executeUpdate();   
            }

            
            if( ! ( disco.getAutore() == null || disco.getAutore().getNome().isEmpty() || disco.getAutore().getCognome().isEmpty() ) ){
                String sqlAutore = " SELECT * FROM autore WHERE nome = " + disco.getAutore().getNome() +" AND cognome = " + disco.getAutore().getCognome();
                PreparedStatement pstAutore = con.prepareStatement(sqlAutore);
                ResultSet rsAutore = pstAutore.executeQuery(); 
                if( rsAutore.next() ){
                    sqlUpdateDisco = "UPDATE disco SET id_autore = "+ rsAutore.getString("id") +" WHERE id = " + idDisco;
                    pstUpdateDisco = con.prepareStatement(sqlUpdateDisco);
                    pstUpdateDisco.executeUpdate();
                } 
                else {
                    sqlAutore = " INSERT INTO autore ( nome, cognome ) VALUES ( " + disco.getAutore().getNome() + " , " + disco.getAutore().getCognome() + " )";
                    pstAutore = con.prepareStatement(sqlAutore);
                    pstAutore.execute(); 
                    
                    sqlAutore = " SELECT id FROM autore WHERE nome = " + disco.getAutore().getNome() +" AND cognome = " + disco.getAutore().getCognome();
                    pstAutore = con.prepareStatement(sqlAutore);
                    rsAutore = pstAutore.executeQuery();
                    if( rsAutore.next() ){
                        sqlUpdateDisco = "UPDATE disco SET id_autore = "+ rsAutore.getString("id") +" WHERE id = " + idDisco;
                        pstUpdateDisco = con.prepareStatement(sqlUpdateDisco);
                        pstUpdateDisco.executeUpdate();                      
                    }
                    else { return false; }

                }
            }
            
            if( ! ( disco.getTracce() == null || disco.getTracce().size() == 0 ) ){
                String sqlDeleteTraccia = "DELETE FROM tracce_disco WHERE id_disco = " + idDisco;
                PreparedStatement pstDeleteTraccia = con.prepareStatement(sqlDeleteTraccia);
                pstDeleteTraccia.execute();
                for( Traccia traccia : disco.getTracce() ){
                    String sqlT = " SELECT * FROM traccia WHERE titolo = "+ traccia.getTitolo() +" AND durata = " + traccia.getDurata();
                    PreparedStatement pstT = con.prepareStatement(sqlT);
                    ResultSet rsT = pstT.executeQuery();
                    if( rsT.next() ){
                        sqlT = "INSERT INTO tracce_disco ( id_disco, id_traccia ) VALUES ( " + idDisco + " , " + rsT.getString("id") + ")";
                        pstT = con.prepareStatement(sqlT);
                        pstT.execute();
                    } else {

                        sqlT = " INSERT INTO traccia ( titolo , durata ) VALUES ( " + traccia.getTitolo() + " , " + traccia.getDurata() + " )";
                        pstT = con.prepareStatement(sqlT);
                        pstT.execute(); 
                        sqlT = " SELECT id FROM traccia WHERE titolo = " + traccia.getTitolo() + " AND durata = " + traccia.getDurata();
                        pstT = con.prepareStatement(sqlT);
                        rsT = pstT.executeQuery();
                        if( rsT.next() ){
                            sqlT = "INSERT INTO tracce_disco ( id_disco, id_traccia ) VALUES ( " + idDisco + " , " + rsT.getString("id") + " ) ";
                            pstT = con.prepareStatement(sqlT);
                            pstT.execute();
                        } 
                        else { return false; }

                    }
                    
                }
            }
            con.close();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
        return true;

    }
    
    @Override
    public String insertDisco( String idCollezione, Disco disco, String idUtente ) throws RepoError {
        
        try{
            ResultSet rsD = null;
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sqlCheckDisco = "SELECT * FROM disco WHERE titolo = " + disco.getTitolo();
            PreparedStatement pstCheckDisco = con.prepareStatement(sqlCheckDisco);
            ResultSet rsCheckDisco = pstCheckDisco.executeQuery();  
            if( rsCheckDisco.next() ){
                String sqlInsertDisco = "INSERT INTO dischi_collezione ( id_collezione, id_disco ) VALUES ( "+ idCollezione + " , "+ rsCheckDisco.getString("id") + ") ";
                PreparedStatement pstInsertDisco = con.prepareStatement(sqlInsertDisco);
                pstInsertDisco.execute();
                return rsCheckDisco.getString("id");
            }
            
            //SELEZIONO L'ID DELL'AUTORE
            String sql = "SELECT * FROM autore WHERE nome = "+ disco.getAutore().getNome() +" AND cognome = "+ disco.getAutore().getCognome();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();             
            if( rs.next() ){
                String sqlInsertDisco = "INSERT INTO disco ( id_autore, titolo ) VALUES ( "+ rs.getString("id") + " , "+ disco.getTitolo() + ") ";
                PreparedStatement pstInsertDisco = con.prepareStatement(sqlInsertDisco);
                pstInsertDisco.execute();
                
                String sqlD = "SELECT * FROM disco WHERE titolo = "+ disco.getTitolo();
                PreparedStatement pstD = con.prepareStatement(sqlD);
                rsD = pstD.executeQuery(); 
                if( rsD.next() ){
                   for( Traccia traccia : disco.getTracce() ){
                        String sqlT = "SELECT * FROM traccia WHERE titolo = "+ traccia.getTitolo() +" AND durata = "+ traccia.getDurata();
                        PreparedStatement pstT = con.prepareStatement(sqlT);
                        ResultSet rsT = pstT.executeQuery();  
                        if( ! rsT.next() ){
                            String sqlInsertT = "INSERT INTO traccia ( titolo , durata ) VALUES ( "+ traccia.getTitolo() + " , "+ traccia.getDurata() + ") ";
                            PreparedStatement pstInsertT = con.prepareStatement(sqlInsertT);
                            pstInsertT.execute();

                            sqlT = "SELECT * FROM traccia WHERE titolo = "+ traccia.getTitolo() +" AND durata = "+ traccia.getDurata();
                            pstT = con.prepareStatement(sqlT);
                            rsT = pstT.executeQuery();
                        } 
                        String sqlInsertTD = "INSERT INTO tracce_disco ( id_traccia , id_disco ) VALUES ( "+ rsT.getString("id") + " , "+ rsD.getString("id") +" ) ";
                        PreparedStatement pstInsertTD = con.prepareStatement(sqlInsertTD);
                        pstInsertTD.execute();
                    } 
                } else { return null; }
                
                
            } else {
                String sqlInsert = "INSERT INTO autore ( nome, cognome ) VALUES ( "+ disco.getAutore().getNome() +" , "+ disco.getAutore().getCognome() + ") ";
                PreparedStatement pstInsert = con.prepareStatement(sqlInsert);
                pstInsert.execute();

                String sqlAutore = "SELECT * FROM autore WHERE nome = "+ disco.getAutore().getNome() +" AND cognome = "+ disco.getAutore().getCognome();
                PreparedStatement pstAutore = con.prepareStatement(sqlAutore);
                ResultSet rsAutore = pstAutore.executeQuery();
                if( rsAutore.next() ){
                    String sqlInsertDisco = "INSERT INTO disco ( id_autore, titolo ) VALUES ( "+ rsAutore.getString("id") + " , "+ disco.getTitolo() + ") ";
                    PreparedStatement pstInsertDisco = con.prepareStatement(sqlInsertDisco);
                    pstInsertDisco.execute();
                    
                    String sqlD = "SELECT * FROM disco WHERE titolo = "+ disco.getTitolo();
                    PreparedStatement pstD = con.prepareStatement(sqlD);
                    rsD = pstD.executeQuery();  
                    if( rsD.next() ){
                        for( Traccia traccia : disco.getTracce() ){
                            String sqlT = "SELECT * FROM traccia WHERE titolo = "+ traccia.getTitolo() +" AND durata = "+ traccia.getDurata();
                            PreparedStatement pstT = con.prepareStatement(sqlT);
                            ResultSet rsT = pstT.executeQuery();  
                            if( ! rsT.next() ){
                                String sqlInsertT = "INSERT INTO traccia ( titolo , durata ) VALUES ( "+ traccia.getTitolo() + " , "+ traccia.getDurata() + ") ";
                                PreparedStatement pstInsertT = con.prepareStatement(sqlInsertT);
                                pstInsertT.execute();

                                sqlT = "SELECT * FROM traccia WHERE titolo = "+ traccia.getTitolo() +" AND durata = "+ traccia.getDurata();
                                pstT = con.prepareStatement(sqlT);
                                rsT = pstT.executeQuery();
                                if( ! rsT.next() ){ return null; }
                            } 
                            String sqlInsertTD = "INSERT INTO tracce_disco ( id_traccia , id_disco ) VALUES ( "+ rsT.getString("id") + " , "+ rsD.getString("id") +" ) ";
                            PreparedStatement pstInsertTD = con.prepareStatement(sqlInsertTD);
                            pstInsertTD.execute();
                        }  
                    } else { return null; }
                                       
                    
                }
                else { return null; }
            }
            String sqlInsertDisco = "INSERT INTO dischi_collezione ( id_collezione, id_disco ) VALUES ( "+ idCollezione + " , "+ rsD.getString("id") + ") ";
            PreparedStatement pstInsertDisco = con.prepareStatement(sqlInsertDisco);
            pstInsertDisco.execute();
            return rsD.getString("id");
        }
        catch (Exception ex) { return null; }
    }
    
    
    
}
