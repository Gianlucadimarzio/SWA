package model;

import java.util.List;
import javax.ws.rs.core.UriInfo;

public interface DiscoRepoAPI {

    List<String> getDischiByCollezione( Collezione c , UriInfo uribuilder ) throws RepoError;
    Disco getDisco(String id) throws RepoError;
    boolean updateDisco( Disco disco, String idDisco  ) throws RepoError;
    String insertDisco( String idCollezione, Disco d, String idUtente ) throws RepoError;
}
