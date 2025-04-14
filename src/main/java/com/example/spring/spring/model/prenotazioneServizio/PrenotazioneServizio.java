package com.example.spring.spring.model.prenotazioneServizio;

import java.time.LocalDate;

public class PrenotazioneServizio {

    private String id;
    private String idServizio;
    private String idCliente;
    private String nomeServizio;
    private String nomeCliente;
    private String cognomeCliente;
    private LocalDate dataInizio;
    private int numeroGiorni;
    private int quantitaPrenotata;
    private double prezzoTotale;
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

}
