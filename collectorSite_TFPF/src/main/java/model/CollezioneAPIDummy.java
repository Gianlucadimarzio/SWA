package model;

import static java.lang.Integer.parseInt;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriInfo;
import resources.CollezioneCondivisaResource;
import resources.CollezionePrivataResource;
import resources.CollezioneResource;
import resources.CollezioniResource;
import resources.DischiResource;


public class CollezioneAPIDummy implements CollezioneRepoAPI {    

    @Override
    public List<Collezione> getCollezioni( String privacy ) throws RepoError {
        List<Collezione> collezioni = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE privacy =\'" + privacy + "\'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setId(rs.getString("id"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
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
    public List<Collezione> getCollezioniPubbliche() throws RepoError {
        List<Collezione> collezioni = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE privacy = 'pubblica'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setId(rs.getString("id"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
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
    public List<Collezione> getCollezioniPrivate( String id ) throws RepoError {
        List<Collezione> collezioni = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE privacy = 'privata' AND id_utente = " + id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setId(rs.getString("id"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
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

    //COLLEZIONI CONDIVISE 
    @Override
    public List<CollezioneCondivisa> getCollezioniCondivise( String id ) throws RepoError {
        List<CollezioneCondivisa> collezioni = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE privacy = 'condivisa' AND id_utente = " + id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                CollezioneCondivisa collezioneC = new CollezioneCondivisa();
                collezione.setId(rs.getString("id"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
                collezioneC.setCollezione( collezione );
                String sqlUtenti = "SELECT u.id as idU, username FROM collezione_condivisa  c JOIN utente u ON ( c.id_utente = u.id ) WHERE id_collezione = " + rs.getString("id");
                PreparedStatement pstUtenti = con.prepareStatement(sqlUtenti);
                ResultSet rsUtenti = pstUtenti.executeQuery();
                List<Utente> utenti = new ArrayList();
                while( rsUtenti.next() ){
                    Utente u = new Utente();
                    u.setId(rsUtenti.getString("idU"));
                    u.setUsername(rsUtenti.getString("username"));
                    utenti.add( u );
                }
                collezioneC.setUtenteCondivisione( utenti );
                collezioni.add( collezioneC );
                
            }
            
            sql = "SELECT collezione.id as idC, titolo, privacy FROM collezione, collezione_condivisa WHERE privacy = 'condivisa' AND collezione_condivisa.id_utente = " + id +" AND collezione.id_utente <> collezione_condivisa.id_utente";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while( rs.next() ){
                Collezione collezione = new Collezione();
                CollezioneCondivisa collezioneC = new CollezioneCondivisa();
                collezione.setId(rs.getString("idC"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
                collezioneC.setCollezione( collezione );
                String sqlUtenti = "SELECT u.id as idU, username FROM collezione_condivisa  c JOIN utente u ON ( c.id_utente = u.id ) WHERE id_collezione = " + rs.getString("idC");
                PreparedStatement pstUtenti = con.prepareStatement(sqlUtenti);
                ResultSet rsUtenti = pstUtenti.executeQuery();
                List<Utente> utenti = new ArrayList();
                while( rsUtenti.next() ){
                    Utente u = new Utente();
                    u.setId(rsUtenti.getString("idU"));
                    u.setUsername(rsUtenti.getString("username"));
                    utenti.add( u );
                }
                collezioneC.setUtenteCondivisione( utenti );
                collezioni.add( collezioneC );
                
            }
            
            
            
            con.close();

            return collezioni;
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
    public boolean checkUtenteShare( String idCollezione, String idUtenteShare, String idUtenteLog ){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con; String sql; PreparedStatement pst; ResultSet rs; boolean flag = false;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            if( !idUtenteShare.equals(idUtenteLog) ){
                sql = "SELECT * FROM collezione WHERE id = " + idCollezione +" AND id_utente = " + idUtenteShare;
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() ){ flag = true; }
                
                sql = "SELECT * FROM collezione_condivisa WHERE id_collezione = " + idCollezione +" AND id_utente = " + idUtenteShare;
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() ){ flag = true; }
                
                if( flag == false ){ return false; }
            }
            //L'UTENTE LOGGATO E' IL CREATORE DEDLLA COLLEZIONE        
            sql = "SELECT * FROM collezione WHERE id = " + idCollezione +" AND id_utente = " + idUtenteLog;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();            
            if( rs.next() ){ return true; }
            
            //L'UTENTE LOGGATO CONDIVIDE QUELLA COLLEZIONE
            sql = "SELECT * FROM collezione_condivisa WHERE id_collezione = " + idCollezione +" AND id_utente = " + idUtenteLog;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if( rs.next() ){ return true; }
            
            return false;
            
        }
        catch (Exception ex) { return false; } 
    }
     
    
    //COLLEZIONI CONDIVISE CON UNO SPECIFICO UTENTE
    @Override
    public List<Collezione> getCollezioniCondivise(String utente, String utenteShare) throws RepoError {
        List<Collezione> l = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT collezione.id_utente as idU, collezione.id as idC, collezione.titolo as titoloC, collezione.privacy as privacyC FROM collezione_condivisa, collezione WHERE collezione_condivisa.id_collezione = collezione.id AND collezione_condivisa.id_utente = " + utenteShare + " AND collezione.id_utente = " + utente;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setId( rs.getString( "idC" ) );  
                collezione.setTitolo( rs.getString( "titoloC" ) );  
                collezione.setPrivacy( rs.getString( "privacyC" ) ); 
                
                String sqlUtente = "SELECT * FROM utente WHERE id =" + rs.getString("idU");
                PreparedStatement pstUtente = con.prepareStatement(sqlUtente);
                ResultSet rsUtente = pstUtente.executeQuery();               
                Utente u = new Utente(); 
                if( rsUtente.next() ){
                    u.setUsername( rsUtente.getString("username") );
                    u.setId( rsUtente.getString("id") );
                    collezione.setUtente(u);
                }
                else { return null; }
                String sqlDischi = "SELECT d.id as idD, d.titolo as titoloD FROM dischi_collezione dc JOIN disco d ON (d.id=dc.id_disco) WHERE dc.id_collezione = " +rs.getString("idC");
                PreparedStatement pstDischi = con.prepareStatement(sqlDischi);
                ResultSet rsDischi = pstDischi.executeQuery();
                List<Disco> dischi = new ArrayList();
                while( rsDischi.next() ){
                    Disco disco = new Disco();
                    disco.setId(rsDischi.getString("idD"));
                    disco.setTitolo(rsDischi.getString("titoloD"));
                    dischi.add(disco);
                }
                collezione.setDischi(dischi);             
                l.add(collezione);
            }
            
            sql = "SELECT collezione.id as idC, titolo, privacy FROM collezione, collezione_condivisa WHERE privacy = 'condivisa' AND collezione_condivisa.id_utente = " + utente;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while( rs.next() ){

                Collezione collezione = new Collezione();
                collezione.setId(rs.getString("idC"));
                collezione.setTitolo(rs.getString("titolo"));
                collezione.setPrivacy(rs.getString("privacy"));
                String sqlUtente = "SELECT * FROM utente WHERE id =" + utente;
                PreparedStatement pstUtente = con.prepareStatement(sqlUtente);
                ResultSet rsUtente = pstUtente.executeQuery();               
                Utente u = new Utente(); 
                if( rsUtente.next() ){
                    u.setUsername( rsUtente.getString("username") );
                    u.setId( rsUtente.getString("id") );
                    collezione.setUtente(u);
                }
                else { return null; }
                String sqlDischi = "SELECT d.id as idD, d.titolo as titoloD FROM dischi_collezione dc JOIN disco d ON (d.id=dc.id_disco) WHERE dc.id_collezione = " +rs.getString("idC");
                PreparedStatement pstDischi = con.prepareStatement(sqlDischi);
                ResultSet rsDischi = pstDischi.executeQuery();
                List<Disco> dischi = new ArrayList();
                while( rsDischi.next() ){
                    Disco disco = new Disco();
                    disco.setId(rsDischi.getString("idD"));
                    disco.setTitolo(rsDischi.getString("titoloD"));
                    dischi.add(disco);
                }
                collezione.setDischi(dischi);
                l.add( collezione );
            }
            
            con.close();
            return l;
        }
        catch (SQLException ex) { return null; }
        catch (ClassNotFoundException ex) { return null; }
    }
    
    @Override
    public Collezione getCollezione(String id) throws RepoError { 
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT * FROM collezione WHERE id = " + id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                Collezione collezione = new Collezione();
                collezione.setId( rs.getString( "id" ) );  
                collezione.setTitolo( rs.getString( "titolo" ) );  
                collezione.setPrivacy( rs.getString( "privacy" ) ); 
                
                String sqlUtente = "SELECT * FROM utente WHERE id =" + rs.getString("id_utente");
                PreparedStatement pstUtente = con.prepareStatement(sqlUtente);
                ResultSet rsUtente = pstUtente.executeQuery();               
                Utente u = new Utente(); 
                if( rsUtente.next() ){
                    u.setUsername( rsUtente.getString("username") );
                    u.setId( rsUtente.getString("id") );
                    collezione.setUtente(u);
                }
                else { return null; }
                String sqlDischi = "SELECT d.id as idD, d.titolo as titoloD FROM dischi_collezione dc JOIN disco d ON (d.id=dc.id_disco) WHERE dc.id_collezione = " +rs.getString("id");
                PreparedStatement pstDischi = con.prepareStatement(sqlDischi);
                ResultSet rsDischi = pstDischi.executeQuery();
                List<Disco> dischi = new ArrayList();
                while( rsDischi.next() ){
                    Disco disco = new Disco();
                    disco.setId(rsDischi.getString("idD"));
                    disco.setTitolo(rsDischi.getString("titoloD"));
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

    @Override
    public List<Disco> getDischi(String id) throws RepoError {
        List<Disco> dischi = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root",""); 
            String sql = "SELECT d.id as idD, d.titolo as titoloD FROM dischi_collezione dc JOIN disco d ON (d.id=dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.id = " + id;
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
    public List<String> searchDiscoPrivato( String nomeDisco, String maxDurata, String minDurata, String idUser, UriInfo uribuilder ){
        List<String> l = new ArrayList();
        if( maxDurata.isEmpty() ){ maxDurata = "999999"; }
        if( minDurata.isEmpty() ){ minDurata = "0"; }
        String sql;
        if( nomeDisco.isEmpty() ){
            sql = "SELECT disco.id as idD, collezione.id as idC FROM disco, dischi_collezione, collezione WHERE collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'privata' AND collezione.id_utente = " + idUser;
        }
        else{
            sql = "SELECT disco.id as idD, collezione.id as idC FROM disco, dischi_collezione, collezione WHERE collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'privata' AND disco.titolo = '" + nomeDisco + "' AND collezione.id_utente = " + idUser;
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            String idDisco = null; String idCollezione = null;  
            while( rs.next() ){
                int durataTot = 0;
                idDisco = rs.getString("idD"); 
                idCollezione = rs.getString("idC");
                
                String sqlTraccia = "SELECT * from tracce_disco, traccia WHERE tracce_disco.id_traccia = traccia.id AND tracce_disco.id_disco = " + idDisco;
                PreparedStatement pstTraccia = con.prepareStatement(sqlTraccia);
                ResultSet rsTraccia = pstTraccia.executeQuery();
                while( rsTraccia.next() ){
                    durataTot += rsTraccia.getInt("durata");
                }
                URI uri =  null;
                if( durataTot >= parseInt(minDurata) && durataTot <= parseInt(maxDurata)   ){
                    uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePrivata").path(CollezionePrivataResource.class, "getDischi").path(DischiResource.class, "getDisco").build( idCollezione, idDisco );
                    l.add( uri.toString() );
                }
            }
            return l; 
        }
        catch( Exception e ){ return null; }        
    }
    
    @Override
    public List<String> searchDiscoPubblico( String nomeDisco, String maxDurata, String minDurata, UriInfo uribuilder ){
        List<String> l = new ArrayList();
        if( maxDurata.isEmpty() ){ maxDurata = "999999"; }
        if( minDurata.isEmpty() ){ minDurata = "0"; }
        String sql;
        if( nomeDisco.isEmpty() ){
            sql = "SELECT disco.id as idD, collezione.id as idC FROM disco, dischi_collezione, collezione WHERE collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'pubblica'";
        }
        else{
            sql = "SELECT disco.id as idD, collezione.id as idC FROM disco, dischi_collezione, collezione WHERE collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'pubblica' AND disco.titolo = '" + nomeDisco + "' ";
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            String idDisco = null; String idCollezione = null;  
            while( rs.next() ){
                int durataTot = 0;
                idDisco = rs.getString("idD"); 
                idCollezione = rs.getString("idC");
                
                String sqlTraccia = "SELECT * from tracce_disco, traccia WHERE tracce_disco.id_traccia = traccia.id AND tracce_disco.id_disco = " + idDisco;
                PreparedStatement pstTraccia = con.prepareStatement(sqlTraccia);
                ResultSet rsTraccia = pstTraccia.executeQuery();
                while( rsTraccia.next() ){
                    durataTot += rsTraccia.getInt("durata");
                }
                URI uri =  null;
                if( durataTot >= parseInt(minDurata) && durataTot <= parseInt(maxDurata)   ){
                    uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezionePubblica").path(CollezioneResource.class, "getDischi").path(DischiResource.class, "getDisco").build( idCollezione, idDisco );
                    l.add( uri.toString());
                }
            }
            return l; 
        }
        catch( Exception e ){ return null; }        
    }
    
    @Override
    public List<String> searchDiscoCondiviso( String nomeDisco, String maxDurata, String minDurata, String idUser, UriInfo uribuilder ){
        List<String> l = new ArrayList();
        if( maxDurata.isEmpty() ){ maxDurata = "999999999"; }
        if( minDurata.isEmpty() ){ minDurata = "0"; }
        String sql;
        if( nomeDisco.isEmpty() ){
            sql = "SELECT disco.id as idD, collezione.id as idC, collezione_condivisa.id_utente as idU FROM disco, dischi_collezione, collezione, collezione_condivisa WHERE collezione.id = collezione_condivisa.id_collezione AND collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'condivisa' AND ( collezione.id_utente = "+ idUser +" OR collezione_condivisa.id_utente = "+ idUser +" )";
        }
        else{
            sql = "SELECT disco.id as idD, collezione.id as idC, collezione_condivisa.id_utente as idU FROM disco, dischi_collezione, collezione, collezione_condivisa WHERE collezione.id = collezione_condivisa.id_collezione AND collezione.id = dischi_collezione.id_collezione AND disco.id = dischi_collezione.id_disco AND privacy = 'condivisa' AND disco.titolo = '" + nomeDisco + "' AND ( collezione.id_utente = " + idUser +" OR collezione_condivisa.id_utente = "+ idUser +")";
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            String idDisco = null; String idCollezione = null; String idUtenteShare = null;    
            while( rs.next() ){
                int durataTot = 0;
                idDisco = rs.getString("idD"); 
                idCollezione = rs.getString("idC");
                idUtenteShare = rs.getString("idU");
                String sqlTraccia = "SELECT * from tracce_disco, traccia WHERE tracce_disco.id_traccia = traccia.id AND tracce_disco.id_disco = " + idDisco;
                PreparedStatement pstTraccia = con.prepareStatement(sqlTraccia);
                ResultSet rsTraccia = pstTraccia.executeQuery();
                while( rsTraccia.next() ){
                    durataTot += rsTraccia.getInt("durata");
                }
                URI uri =  null;
                if( durataTot >= parseInt(minDurata) && durataTot <= parseInt(maxDurata)   ){
                    uri = uribuilder.getBaseUriBuilder().path( CollezioniResource.class).path( CollezioniResource.class, "getCollezioneCondivisa").path(CollezioneCondivisaResource.class, "getDischi").path(DischiResource.class, "getDisco").build( idUtenteShare, idCollezione, idDisco );
                    l.add( uri.toString() );
                }
            }
            return l; 
        }
        catch( Exception e ){ return null; }        
    }
    
    @Override
    public List<Map<String, Object>> getStatistichePubbliche (){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            String sql = "SELECT COUNT(*) as num FROM collezione WHERE privacy = 'pubblica' ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if( rs.next() && ( ! rs.getString("num").equals("0") )  ){
                m.put("NUMERO DI COLLEZIONI PUBBLICHE:", rs.getString("num"));
                
                sql = "SELECT COUNT(idD) AS num FROM (select d.id as idD FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'pubblica' ) as dischi";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI PUBBLICHE: ", rs.getString("num") );
                }
                else{
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI PUBBLICHE: ", 0 );
                }
                
                sql = "SELECT COUNT(idT) AS num, SUM(tracce.durata) as durata, AVG(tracce.durata) as durataMedia  FROM (select t.id as idT, t.durata as durata FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) JOIN tracce_disco td ON (td.id_disco = d.id) JOIN traccia t ON (t.id = td.id_traccia) WHERE c.privacy= 'pubblica' ) as tracce";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI PUBBLICHE: ", rs.getString("num") );
                    m.put("DURATA COMPLESSIVA DELLE TRACCE NELLE COLLEZIONI PUBBLICHE: ", rs.getString("durata"));
                    m.put("DURATA MEDIA DELLE TRACCE NELLE COLLEZIONI PUBBLICHE: ", rs.getString("durataMedia"));

                }
                else{
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI PUBBLICHE: ", 0 );
                }
                
                sql = "SELECT COUNT(idA) AS num FROM (select a.id as idA FROM autore a JOIN disco d ON (a.id = d.id_autore) JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'pubblica' ) as autori";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI PUBBLICHE: ", rs.getString("num") );
                }
                else {
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI PUBBLICHE: ", 0 );

                }
                l.add(m);
            } 
            else {
                m.put("NUMERO DI COLLEZIONI PUBBLICHE: ", 0);
                l.add( m );
                return l;
            }
            
        } 
        catch( Exception e ){ return null; }
        return l;
    }
    
    @Override
    public List<Map<String, Object>> getStatistichePrivate ( String idUser ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            String sql = "SELECT COUNT(*) as num FROM collezione WHERE privacy = 'privata' AND id_utente = "+ idUser;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                m.put("NUMERO DI COLLEZIONI PRIVATE:", rs.getString("num"));
                
                sql = "SELECT COUNT(idD) AS num FROM (select d.id as idD FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'privata' AND c.id_utente = "+ idUser +" ) as dischi";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI PRIVATE: ", rs.getString("num") );
                }
                else{
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI PRIVATE: ", 0 );
                }
                
                sql = "SELECT COUNT(idT) AS num, SUM(tracce.durata) as durata, AVG(tracce.durata) as durataMedia  FROM (select t.id as idT, t.durata as durata FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) JOIN tracce_disco td ON (td.id_disco = d.id) JOIN traccia t ON (t.id = td.id_traccia) WHERE c.privacy= 'privata' AND c.id_utente = "+ idUser +"  ) as tracce";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI PRIVATE: ", rs.getString("num") );
                    m.put("DURATA COMPLESSIVA DELLE TRACCE NELLE COLLEZIONI PRIVATE: ", rs.getString("durata"));
                    m.put("DURATA MEDIA DELLE TRACCE NELLE COLLEZIONI PRIVATE: ", rs.getString("durataMedia"));

                }
                else{
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI PRIVATE: ", 0 );
                }
                
                sql = "SELECT COUNT(idA) AS num FROM (select a.id as idA FROM autore a JOIN disco d ON (a.id = d.id_autore) JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'privata' AND c.id_utente = "+ idUser +" ) as autori";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI PRIVATE: ", rs.getString("num") );
                }
                else {
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI PRIVATE: ", 0 );

                }
                l.add(m);
            } 
            else {
                m.put("NUMERO DI COLLEZIONI PRIVATE: ", 0);
                l.add( m );
            }
            
        } 
        catch( Exception e ){ return null; }
        return l;
    }
    
    @Override
    public List<Map<String, Object>> getStatisticheCondivise ( String idUser ){
        List<Map<String, Object>> l = new ArrayList();
        Map<String, Object> m = new HashMap();
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","root","");
            String sql = "SELECT COUNT(*) as num FROM collezione WHERE privacy = 'condivisa' AND id_utente = "+ idUser;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                m.put("NUMERO DI COLLEZIONI CONDIVISE:", rs.getString("num"));
                
                sql = "SELECT COUNT(idD) AS num FROM (select d.id as idD FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'condivisa' AND c.id_utente = "+ idUser +" ) as dischi";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI CONDIVISE: ", rs.getString("num") );
                }
                else{
                    m.put("NUMERO DI DISCHI PRESENTI NELLE COLLEZIONI CONDIVISE: ", 0 );
                }
                
                sql = "SELECT COUNT(idT) AS num, SUM(tracce.durata) as durata, AVG(tracce.durata) as durataMedia  FROM (select t.id as idT, t.durata as durata FROM disco d JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) JOIN tracce_disco td ON (td.id_disco = d.id) JOIN traccia t ON (t.id = td.id_traccia) WHERE c.privacy= 'condivisa' AND c.id_utente = "+ idUser +"  ) as tracce";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI CONDIVISE: ", rs.getString("num") );
                    m.put("DURATA COMPLESSIVA DELLE TRACCE NELLE COLLEZIONI CONDIVISE: ", rs.getString("durata"));
                    m.put("DURATA MEDIA DELLE TRACCE NELLE COLLEZIONI CONDIVISE: ", rs.getString("durataMedia"));

                }
                else{
                    m.put("NUMERO DI TRACCE PRESENTI NELLE COLLEZIONI CONDIVISE: ", 0 );
                }
                
                sql = "SELECT COUNT(idA) AS num FROM (select a.id as idA FROM autore a JOIN disco d ON (a.id = d.id_autore) JOIN dischi_collezione dc ON (d.id = dc.id_disco) JOIN collezione c ON (c.id = dc.id_collezione) WHERE c.privacy= 'condivisa' AND c.id_utente = "+ idUser +" ) as autori";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if( rs.next() && ( ! rs.getString("num").equals("0") ) ){
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI CONDIVISE: ", rs.getString("num") );
                }
                else {
                    m.put("NUMERO DI AUTORI PRESENTI NELLE COLLEZIONI CONDIVISE: ", 0 );

                }
                l.add(m);
            } 
            else {
                m.put("NUMERO DI COLLEZIONI CONDIVISE: ", 0);
                l.add( m );
            }
            
        } 
        catch( Exception e ){ return null; }
        return l;
    }
}
