package model;

import java.util.ArrayList;
import java.util.List;

public class CollezioneCondivisa {

    private Collezione collezione;
    private List<Utente> utenteCondivisione;


    public CollezioneCondivisa() {
        collezione = new Collezione();
        utenteCondivisione = new ArrayList();

    }

    public Collezione getCollezione() {
        return collezione;
    }

    public void setCollezione(Collezione collezione) {
        this.collezione = collezione;
    }

    public List<Utente> getUtenteCondivisione() {
        return utenteCondivisione;
    }

    public void setUtenteCondivisione(List<Utente> utenteCondivisione) {
        this.utenteCondivisione = utenteCondivisione;
    }
    
}
