package model;

import java.util.List;

public interface AutoreRepoAPI {

    List<Autore> getAutori() throws RepoError;
    Autore getAutore( String id ) throws RepoError;
    List<Disco> getDischi( String id ) throws RepoError;
    Disco getDisco( String idDisco ) throws RepoError;
    List<Traccia> getTracce( String id ) throws RepoError;
    Traccia getTraccia( String idTraccia ) throws RepoError;

}
