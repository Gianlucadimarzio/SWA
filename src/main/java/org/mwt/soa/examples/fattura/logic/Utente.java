package org.mwt.soa.examples.fattura.logic;

public class Utente {

    private String username;
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
