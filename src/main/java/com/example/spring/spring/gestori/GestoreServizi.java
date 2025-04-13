package com.example.spring.spring.gestori;

import com.example.spring.spring.model.servizio.Servizi;
import java.util.List;

public interface GestoreServizi {
    void creaServizio(Servizi servizio);
    List<Servizi> visualizzaServiziCreati(); // O semplicemente visualizzaServiziCreati()
    void modificaServizio(Servizi servizio);
    void eliminaServizio(Servizi servizio);
    void prenotaServizio();
}
