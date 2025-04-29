package com.example.spring.spring.model.prenotazioneServizio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Prenotazione {

    @JsonProperty("id")
    @Id
    private String id;
    @JsonProperty("idServizio")
    private String idServizio;
    @JsonProperty("idCliente")
    private String idCliente;
    @JsonProperty("nomeServizio")
    private String nomeServizio;
    @JsonProperty("nomeCliente")
    private String nomeCliente;
    @JsonProperty("cognomeCliente")
    private String cognomeCliente;
    @JsonProperty("prezzoTotale")
    private double prezzoTotale;


    public Prenotazione() {
        // Costruttore vuoto richiesto da Spring Data
    }

    public Prenotazione(String idServizio, String idCliente, String nomeServizio, String nomeCliente, String cognomeCliente, double prezzoTotale) {

        this.idServizio = idServizio;
        this.idCliente = idCliente;
        this.nomeServizio = nomeServizio;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        //this.dataInizio = dataInizio;
        this.prezzoTotale = prezzoTotale;

    }

    public String getId() {
        return this.id;
    }

    public String getIdServizio() {
        return idServizio;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNomeServizio() {
        return nomeServizio;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getCognomeCliente() {
        return cognomeCliente;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }
}
