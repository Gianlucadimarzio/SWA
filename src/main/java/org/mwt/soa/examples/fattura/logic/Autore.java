package org.mwt.soa.examples.fattura.logic;

public class Autore {

    private String nome;
    private String cognome;


    public Autore() {
        nome = "";
        cognome = "";

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
 

}
