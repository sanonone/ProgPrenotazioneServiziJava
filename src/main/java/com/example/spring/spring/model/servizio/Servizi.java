package com.example.spring.spring.model.servizio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "servizi")
public abstract class Servizi {

    @JsonProperty("id")
    @Id
    protected String id;
    @JsonProperty("nome")
    protected String nome;
    @JsonProperty("descrizione")
    protected String descrizione;
    @JsonProperty("prezzo")
    protected double prezzo;

    public Servizi() {
        
    }

    public Servizi(String nome, String descrizione, double prezzo) {
        //this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
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
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    //lista prenotazioni
    //lista clienti
    //
}
