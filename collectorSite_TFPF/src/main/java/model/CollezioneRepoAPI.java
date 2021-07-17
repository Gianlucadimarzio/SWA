package model;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.UriInfo;

public interface CollezioneRepoAPI {

    List<Collezione> getCollezioni( String privacy ) throws RepoError;
    List<Collezione> getCollezioniPubbliche() throws RepoError;
    List<Collezione> getCollezioniPrivate( String id ) throws RepoError;
    List<CollezioneCondivisa> getCollezioniCondivise( String id )throws RepoError;
    List<Collezione> getCollezioniCondivise( String utente, String utenteShare ) throws RepoError;
    Collezione getCollezione(String id) throws RepoError;
    boolean addDiscoToCollezione(String titolo, Disco d) throws RepoError;
    List<Disco> getDischi( String titolo ) throws RepoError;
    boolean checkUtenteShare( String idCollezione, String idUtenteShare ) throws RepoError;
    List<String> searchDiscoPrivato( String nomeDisco, String maxDurata, String minDurata, String idUser, UriInfo uribuilder );
    List<String> searchDiscoPubblico( String nomeDisco, String maxDurata, String minDurata, UriInfo uribuilder );
    List<String> searchDiscoCondiviso( String nomeDisco, String maxDurata, String minDurata, String idUser, UriInfo uribuilder );

}
