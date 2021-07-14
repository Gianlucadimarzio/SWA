package model;

public class CollezioneCondivisa {

    private Collezione collezione;
    private Utente utenteCondivisione;


    public CollezioneCondivisa() {
        collezione = new Collezione();
        utenteCondivisione = new Utente();

    }

    public Collezione getCollezione() {
        return collezione;
    }

    public void setCollezione(Collezione collezione) {
        this.collezione = collezione;
    }

    public Utente getUtenteCondivisione() {
        return utenteCondivisione;
    }

    public void setUtenteCondivisione(Utente utenteCondivisione) {
        this.utenteCondivisione = utenteCondivisione;
    }
    
}
