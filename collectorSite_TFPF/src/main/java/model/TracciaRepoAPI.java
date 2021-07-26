package model;



public interface TracciaRepoAPI {

    Traccia getTraccia(String id) throws RepoError;
}
