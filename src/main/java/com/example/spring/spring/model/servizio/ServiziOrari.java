package com.example.spring.spring.model.servizio;

import com.example.spring.spring.interfacce.Prenotabile;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiziOrari extends Servizi implements Prenotabile<PrenotazioneServizioOrario> {
    protected List<TimeInterval> fasceOrarie;
    private int disponibilitaPerFascia;

    // Mappa: data → (fascia → motorini già prenotati)
    private final Map<LocalDate, Map<TimeInterval, Integer>> prenotazioni = new HashMap<>();


    public ServiziOrari(String id, String nome, String descrizione, double prezzo, List<TimeInterval> fasceOrarie, int disponibilitaPerFascia) {
        super(nome, descrizione, prezzo);
        this.fasceOrarie = new ArrayList<>(fasceOrarie);;
        this.disponibilitaPerFascia = disponibilitaPerFascia;
    }

    @Override
    public boolean prenota(PrenotazioneServizioOrario prenotazione) {

        return true;
    }

}
