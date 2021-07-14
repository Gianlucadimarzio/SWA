package model;

import java.net.URI;
import java.util.List;

public interface CollezioneRepoAPI {

    List<Collezione> getCollezioni() throws RepoError;
    Collezione getCollezione(String titolo) throws RepoError;
    boolean addDiscoToCollezione(String titolo, Disco d) throws RepoError;
}
