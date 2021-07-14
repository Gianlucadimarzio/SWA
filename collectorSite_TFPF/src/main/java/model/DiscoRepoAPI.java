package model;

import java.net.URI;
import java.util.List;

public interface DiscoRepoAPI {

    List<Disco> getDischi() throws RepoError;
    Disco getDisco(String titolo) throws RepoError;
}
