package model;

public class Traccia {

    private String titolo;
    private int durata;

    public Traccia() {
        titolo = "";
        durata = 0;

    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
    
}
