package com.example.spring.spring.model.persona;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public abstract class Persona {
    @JsonProperty("id")
    @Id
    protected String id;
    @JsonProperty("nome")
    protected String nome;
    @JsonProperty("cognome")
    protected String cognome;
    @JsonProperty("eta")
    protected int eta;
    @JsonProperty("telefono")
    protected int telefono;

    public Persona() {
    }

    public Persona( String nome, String cognome, int eta, int telefono) {
        //this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.telefono = telefono;
    }
    // Getters e Setters

    /*
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public int getEta() {
        return eta;
    }
    public void setEta(int eta) {
        this.eta = eta;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
*/

}
