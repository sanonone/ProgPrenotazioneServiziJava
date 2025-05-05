package com.example.spring.spring.model.servizio;

import com.example.spring.spring.interfacce.Prenotabile;
import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ServiziGiornalieri extends Servizi implements Prenotabile<PrenotazioneServizio> {

    @JsonProperty("disponibilita")
    private int disponibilita;
    @JsonProperty("prenotazioniPerData")
    protected Map<LocalDate, Integer> prenotazioniPerData;

    public ServiziGiornalieri() {
        super();
        this.disponibilita = 0;
        this.prenotazioniPerData = new HashMap<>();
    }

    public ServiziGiornalieri(String nome, String descrizione, double prezzo, int disponibilita) {
        super(nome, descrizione, prezzo);
        this.disponibilita = disponibilita;
        this.prenotazioniPerData = new HashMap<>();//istanzia la mappa dell'interfaccia Map di prenotazioniPerData
    }

    //verifica disponibilità
    public boolean verificaDisponibilita(LocalDate dataInizio, int numeroGiorni, int quantitaPrenotata) {
        LocalDate dataFine = dataInizio.plusDays(numeroGiorni<2?0:numeroGiorni-1);
        LocalDate dataCorrente = dataInizio;

        while (!dataCorrente.isAfter(dataFine)) {
            int prenotazioni = prenotazioniPerData.getOrDefault(dataCorrente, 0);
            if (prenotazioni+quantitaPrenotata > disponibilita) {
                System.out.println("Non disponibile per questa data: "+dataCorrente);
                return false; // Non disponibile per questa data
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        System.out.println("Disponibile per tutte le date");
        return true; // Disponibile per tutte le date
    }

    public void confermaPrenotazione(LocalDate dataInizio, int numeroGiorni, int quantitaPrenotata) {
        LocalDate dataFine = dataInizio.plusDays(numeroGiorni<2?0:numeroGiorni-1);
        LocalDate dataCorrente = dataInizio;

        /*
            Se la dataCorrente è già presente come chiave nella mappa, restituisce il valore associato (che è il numero corrente di prenotazioni per quella data).
            Se la dataCorrente non è ancora presente come chiave nella mappa, restituisce il valore predefinito specificato, che in questo caso è 0.
            Questo significa che se non ci sono ancora prenotazioni per quella data, si parte da zero.

            + 1: A questo valore (il numero corrente di prenotazioni o 0 se non presente) viene aggiunto 1, poiché stiamo confermando una nuova prenotazione per questa data.
         */

        while (!dataCorrente.isAfter(dataFine)) {
            prenotazioniPerData.put(dataCorrente, prenotazioniPerData.getOrDefault(dataCorrente, 0) + quantitaPrenotata);
            dataCorrente = dataCorrente.plusDays(1);
        }
    }

    @Override
    public boolean prenota(PrenotazioneServizio prenotazione) {
        boolean disponibile = verificaDisponibilita(
                prenotazione.getDataInizio(),
                prenotazione.getNumeroGiorni(),
                prenotazione.getQuantitaPrenotata()
        );

        if (disponibile) {
            confermaPrenotazione(
                    prenotazione.getDataInizio(),
                    prenotazione.getNumeroGiorni(),
                    prenotazione.getQuantitaPrenotata()
            );
            return true;
        }

        return false;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public Map<LocalDate, Integer> getPrenotazioniPerData() {
        return prenotazioniPerData;
    }
}

