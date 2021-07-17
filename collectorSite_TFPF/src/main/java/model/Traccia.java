package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Traccia {
    @JsonIgnore
    private String id;
    private String titolo;
    private String durata;

    public Traccia() {
        id = "";
        titolo = "";
        durata = "";

    }
    
    public String getId() {
        return id;
    }
    
    public void setId( String id ){
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }
    
}
