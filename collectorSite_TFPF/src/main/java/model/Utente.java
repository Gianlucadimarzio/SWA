package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Utente {

    private String username;
    @JsonIgnore
    private String password;

    public Utente() {

        username = "";
        password = "";
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
