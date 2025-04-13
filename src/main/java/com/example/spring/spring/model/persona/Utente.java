package com.example.spring.spring.model.persona;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.spring.spring.gestori.GestoreServizi;
import com.example.spring.spring.model.servizio.Servizi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Document(collection = "utenti")
public class Utente extends Persona implements GestoreServizi {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("ruolo")
    protected String ruolo;
    @JsonProperty("dataAssunzione")
    protected LocalDate dataAssunzione;

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

    @Override
    public void creaServizio(Servizi servizio) {
        // ... implementazione ...
    }

    @Override
    public List<Servizi> visualizzaServiziCreati() {
        // ... implementazione ...
        return null;
    }

    @Override
    public void modificaServizio(Servizi servizio) {
        // ... implementazione ...
    }

    @Override
    public void eliminaServizio(Servizi servizio) {
        // ... implementazione ...
    }

    @Override
    public void prenotaServizio() {
        // ... implementazione ...
    }

}
