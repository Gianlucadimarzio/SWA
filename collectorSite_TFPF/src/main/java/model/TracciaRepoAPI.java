package model;

import java.net.URI;
import java.util.List;

public interface TracciaRepoAPI {

    Traccia getTraccia(String id) throws RepoError;
}
