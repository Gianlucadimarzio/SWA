package org.mwt.soa.examples.fattura.logic;

import java.util.ArrayList;
import java.util.List;


public class Collezione {

    private String titolo;
    private Utente utente;
    private List<Disco> dischi;
    private String privacy;

    public Collezione() {
        titolo = "";
        utente = new Utente();
        dischi = new ArrayList<>();
        privacy = "";

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    public List<Disco> getDischi() {
        return dischi;
    }

    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
    }
    
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    
}
