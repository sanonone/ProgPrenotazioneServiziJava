package com.example.spring.spring.model.prenotazioneServizio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PrenotazioneServizio extends Prenotazione {

    @JsonProperty("dataInizio")
    private LocalDate dataInizio;
    @JsonProperty("numeroGiorni")
    private int numeroGiorni;
    @JsonProperty("quantitaPrenotata")
    private int quantitaPrenotata;
    @JsonProperty("dataGestione")
    private LocalDate dataGestione;

    public PrenotazioneServizio() {
        // Costruttore vuoto richiesto da Spring Data
    }

    public PrenotazioneServizio(String idServizio, String idCliente, String nomeServizio, String nomeCliente, String cognomeCliente, String dataInizio, int numeroGiorni, int quantitaPrenotata, double prezzoTotale) {

        super(idServizio, idCliente, nomeServizio, nomeCliente, cognomeCliente, prezzoTotale);
        this.numeroGiorni = numeroGiorni;
        this.quantitaPrenotata = quantitaPrenotata;
        this.dataGestione = LocalDate.now();

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataInizio = LocalDate.parse(dataInizio, formatter);
            System.out.println("la data è: "+dataInizio);

        } catch (DateTimeParseException e) {
            System.out.println("Errore nella conversione della data: " + e.getMessage());
        }
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

    public LocalDate getDataGestione() {
        return dataGestione;
    }

    @Override
    public String toString() {
        return "PrenotazioneServizio{\n" +
                "id='" + getId()+
                "\nidServizio='" + getIdServizio()+
                "\nidCliente='" + getIdCliente()+
                "\nnomeServizio='" + getNomeServizio()+
                "\nnomeCliente='" + getNomeCliente()+
                "\ncognomeCliente='" + getCognomeCliente()+
                "\nprezzoTotale=" + getPrezzoTotale() +
                "\ndataInizio=" + dataInizio +
                "\nnumeroGiorni=" + numeroGiorni +
                "\nquantitaPrenotata=" + quantitaPrenotata +
                "\ndataGestione=" + dataGestione +
                '}';
    }
}
