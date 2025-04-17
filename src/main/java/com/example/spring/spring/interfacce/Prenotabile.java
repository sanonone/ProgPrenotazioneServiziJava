package com.example.spring.spring.interfacce;

import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;

//interfaccia generica in modo da poterla usare sia in servizi per data che per quello orari senza doverne creare due
public interface Prenotabile<T> {
    boolean prenota(T prenotazione);
}
