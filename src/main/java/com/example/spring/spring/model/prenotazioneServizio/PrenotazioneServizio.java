package com.example.spring.spring.model.prenotazioneServizio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

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

    public PrenotazioneServizio(String id, String idServizio, String idCliente, String nomeServizio, String nomeCliente, String cognomeCliente, LocalDate dataInizio, int numeroGiorni, int quantitaPrenotata, double prezzoTotale, LocalDate dataGestione) {
        this.id = id;
        this.idServizio = idServizio;
        this.idCliente = idCliente;
        this.nomeServizio = nomeServizio;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.dataInizio = dataInizio;
        this.numeroGiorni = numeroGiorni;
        this.quantitaPrenotata = quantitaPrenotata;
        this.prezzoTotale = prezzoTotale;
        this.dataGestione = dataGestione;
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
