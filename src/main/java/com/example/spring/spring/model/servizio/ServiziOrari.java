package com.example.spring.spring.model.servizio;

import java.util.List;

public class ServiziOrari extends Servizi {
    protected List<TimeInterval> fasceOrarie;

    public ServiziOrari(String id, String nome, String descrizione, int quantita, double prezzo, List<TimeInterval> fasceOrarie) {
        super(id, nome, descrizione, quantita, prezzo);
        this.fasceOrarie = fasceOrarie;
    }

}
