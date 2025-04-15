package com.example.spring.spring.gestori;

import com.example.spring.spring.model.servizio.Servizi;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;

import java.util.List;

public interface GestoreServiziGiornalieri {
    ServiziGiornalieri creaServizioGiornaliero(String nome, String descrizione, double prezzo, int disponibilita);
    List<ServiziGiornalieri> visualizzaServiziGiornalieriCreati(); // O semplicemente visualizzaServiziCreati()
    void modificaServizioGiornaliero(ServiziGiornalieri servizio);
    void eliminaServizioGiornaliero(String id);
    ServiziGiornalieri servizioGiornalieroById(String id);
}
