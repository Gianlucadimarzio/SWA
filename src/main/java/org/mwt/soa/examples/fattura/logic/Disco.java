package org.mwt.soa.examples.fattura.logic;

import java.util.ArrayList;
import java.util.List;

public class Disco {

    private String titolo;
    private Autore autore;
    private List<Traccia> tracce;


    public Disco() {
        titolo = "";
        autore = new Autore();
        tracce = new ArrayList<Traccia>();

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }
    
    public List<Traccia> getTracce() {
        return tracce;
    }

    public void setTracce(List<Traccia> tracce) {
        this.tracce = tracce;
    }
    
}
