package com.example.spring.spring.model.persona;

import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.spring.spring.gestori.GestoreServiziGiornalieri;
import com.example.spring.spring.model.servizio.Servizi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Document(collection = "utenti")
public class Utente extends Persona {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("ruolo")
    private String ruolo;
    @JsonProperty("dataAssunzione")
    private LocalDate dataAssunzione;


    public Utente() {//per json con conjackson
        super();
    }

    public Utente(String nome, String cognome, int eta, int telefono, String username, String password, String ruolo, String dataAssunzione) {
        super(nome, cognome, eta, telefono);
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataAssunzione = LocalDate.parse(dataAssunzione, formatter);

        } catch (DateTimeParseException e) {
            System.out.println("Errore nella conversione della data: " + e.getMessage());
        }


    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public LocalDate getDataAssunzione() {
        return dataAssunzione;
    }


}
