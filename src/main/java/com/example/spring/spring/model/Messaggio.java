package com.example.spring.spring.model;    
public class Messaggio {
    private long id;
    private String testo;

    // Costruttore vuoto: necessario per la deserializzazione JSON da parte di Jackson/Spring
    public Messaggio() {
    }

    // Costruttore con parametri (opzionale, ma utile)
    public Messaggio(long id, String testo) {
        this.id = id;
        this.testo = testo;
    }

    // Getters e Setters: necessari per Jackson/Spring
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        // Utile per il logging sul server
        return "Messaggio{" +
               "id=" + id +
               ", testo='" + testo + '\'' +
               '}';
    }
}