package com.example.spring.spring.model.prenotazioneServizio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PrenotazioneServizio {

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
    @JsonProperty("dataInizio")
    private LocalDate dataInizio;
    @JsonProperty("numeroGiorni")
    private int numeroGiorni;
    @JsonProperty("quantitaPrenotata")
    private int quantitaPrenotata;
    @JsonProperty("prezzoTotale")
    private double prezzoTotale;
    @JsonProperty("dataGestione")
    private LocalDate dataGestione;

    public PrenotazioneServizio() {
        // Costruttore vuoto richiesto da Spring Data
    }

    public PrenotazioneServizio(String idServizio, String idCliente, String nomeServizio, String nomeCliente, String cognomeCliente, String dataInizio, int numeroGiorni, int quantitaPrenotata, double prezzoTotale) {

        this.idServizio = idServizio;
        this.idCliente = idCliente;
        this.nomeServizio = nomeServizio;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        //this.dataInizio = dataInizio;
        this.numeroGiorni = numeroGiorni;
        this.quantitaPrenotata = quantitaPrenotata;
        this.prezzoTotale = prezzoTotale;
        this.dataGestione = LocalDate.now();

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataInizio = LocalDate.parse(dataInizio, formatter);
            System.out.println("la data è: "+dataInizio);

        } catch (DateTimeParseException e) {
            System.out.println("Errore nella conversione della data: " + e.getMessage());
        }
    }


    public String getId() {
        return id;
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

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public int getNumeroGiorni() {
        return numeroGiorni;
    }

    public int getQuantitaPrenotata() {
        return quantitaPrenotata;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public LocalDate getDataGestione() {
        return dataGestione;
    }
}
