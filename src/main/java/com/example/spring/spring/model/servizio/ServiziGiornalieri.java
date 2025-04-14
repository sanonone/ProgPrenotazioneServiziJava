package com.example.spring.spring.model.servizio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ServiziGiornalieri extends Servizi {

    private int disponibilita;
    protected Map<LocalDate, Integer> prenotazioniPerData;

    public ServiziGiornalieri(String id, String nome, String descrizione, int quantita, double prezzo, int disponibilita) {
        super(id, nome, descrizione, quantita, prezzo);
        this.disponibilita = disponibilita;
        this.prenotazioniPerData = new HashMap<>();//istanzia la mappa dell'interfaccia Map di prenotazioniPerData
    }

    //verifica disponibilità
    public boolean verificaDisponibilita(LocalDate dataInizio, int numeroGiorni, int quantitaPrenotata) {
        LocalDate dataFine = dataInizio.plusDays(numeroGiorni);
        LocalDate dataCorrente = dataInizio;

        while (!dataCorrente.isAfter(dataFine)) {
            int prenotazioni = prenotazioniPerData.getOrDefault(dataCorrente, 0);
            if (prenotazioni+quantitaPrenotata > disponibilita) {
                return false; // Non disponibile per questa data
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        return true; // Disponibile per tutte le date
    }

    public void confermaPrenotazione(LocalDate dataInizio, int numeroGiorni, int quantitaPrenotata) {
        LocalDate dataFine = dataInizio.plusDays(numeroGiorni);
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
}

