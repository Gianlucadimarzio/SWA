package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Utente {

    private String id;
    private String username;
    @JsonIgnore
    private String password;

    public Utente() {
        
        id = "";
        username = "";
        password = "";
    }
    
    public String getId() {
        return id;
    }
    
    public void setId( String id ){
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
