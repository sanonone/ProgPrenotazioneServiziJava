package com.example.spring.spring.model.persona;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public abstract class Persona {
    @JsonProperty("id")
    @Id
    private String id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cognome")
    private String cognome;
    @JsonProperty("eta")
    private int eta;
    @JsonProperty("telefono")
    private int telefono;

    public Persona() {
    }

    public Persona( String nome, String cognome, int eta, int telefono) {
        //this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.telefono = telefono;
    }


    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getEta() {
        return eta;
    }

    public String getCognome() {
        return cognome;
    }

    public int getTelefono() {
        return telefono;
    }
}
