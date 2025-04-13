package com.example.spring.spring.model.servizio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "servizi")
public abstract class Servizi {

    @Id
    protected String id;
    protected String nome;
    protected String descrizione;
    protected int quantita;
    protected double prezzo;

    public Servizi(String id, String nome, String descrizione, int quantita, double prezzo) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }
    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(
            String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
