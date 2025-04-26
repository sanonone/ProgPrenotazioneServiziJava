package com.example.spring.spring.client;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.LocalTime;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.model.servizio.ServiziOrari;
import com.example.spring.spring.model.servizio.TimeInterval;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Client {

    // URL base del nostro server semplice
    private static final String SERVER_URL = "http://localhost:8080/api";

    public static void main(String[] args) {
        System.out.println("--- Client Java Semplice Avviato ---");

        // Creiamo un client HTTP standard (da Java 11 in poi)
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
                .build();

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        ChiamateCliente chiamateCliente = new ChiamateCliente();
        ChiamateUtente chiamateUtente = new ChiamateUtente();
        ChiamateServiziGiornalieri chiamateServiziGiornalieri = new ChiamateServiziGiornalieri();
        ChiamatePrenotazioneGiornaliera chiamatePrenotazioneGiornaliera = new ChiamatePrenotazioneGiornaliera();
        ChiamateServiziOrari chiamateServiziOrari = new ChiamateServiziOrari();
        ChiamatePrenotazioneOraria chiamatePrenotazioneOraria = new ChiamatePrenotazioneOraria();

        try {




            //chiamateCliente.creaCliente("Mario", "Rossi", 99, 348578387,"mario.rossi@example.com");
            //chiamateCliente.getClienteById("67fbb21307b3a35264c9587a");
            //chiamateCliente.getAllClienti();
            //chiamateCliente.deleteCliente("67fbb21307b3a35264c9587a");




            //chiamateUtente.creaUtente("Mario", "Moro", 60, 348578387,"mario.moro@example.com","password","direttore","02/02/2021");
            //chiamateUtente.getAllUtenti();
            //chiamateUtente.getUtenteById("6808f8783694b10175a8aae1");
            //chiamateUtente.deleteUtente("67fe64883a8df7041bc2bbdb");



            //chiamateServiziGiornalieri.creaServizioGiornaliero("Noleggio motorini", "Noleggio motorini", 30, 10);
            //chiamateServiziGiornalieri.getAllServiziGiornalieri();
            //chiamateServiziGiornalieri.getServiziGiornalieriById("67ff9f76d550d46d1e8ed1e4");
            //chiamateServiziGiornalieri.deleteServizioGiornaliero("67ffa71ed550d46d1e8ed1e8");


            //chiamatePrenotazioneGiornaliera.creaPrenotazioneGiornaliera(new PrenotazioneServizio("67ffa52dd550d46d1e8ed1e5", "680a12e3e6356128547377d7", "Noleggio motorini", "Mario", "Rossi", "10/04/2025", 3, 1,60));

            // 1. Creare i singoli oggetti TimeInterval
            /*
            TimeInterval fasciaMattina = new TimeInterval(LocalTime.of(9, 0), LocalTime.of(11, 0)); // 09:00 - 11:00
            TimeInterval fasciaPausaPranzo = new TimeInterval(LocalTime.of(13, 0), LocalTime.of(14, 0)); // 13:00 - 14:00
            TimeInterval fasciaPomeriggio = new TimeInterval(LocalTime.of(15, 0), LocalTime.of(18, 0)); // 15:00 - 18:00

            // 2. Creare una Lista e aggiungere le fasce orarie
            List<TimeInterval> fascePerServizio = new ArrayList<>();
            fascePerServizio.add(fasciaMattina);
            fascePerServizio.add(fasciaPausaPranzo);
            fascePerServizio.add(fasciaPomeriggio);

            chiamateServiziOrari.creaServizioOrario("Prenota ombrellone", "prenotazione ombrellone spiaggia per una fascia oraria", 10.00, null, 5);
            chiamateServiziOrari.getAllServiziOrari();
             */
            //chiamateServiziOrari.creaServizioOrario(new ServiziOrari("Prenota ombrellone", "prenotazione ombrellone spiaggia per una fascia oraria", 10.00, null, 5));



            /*
            TimeInterval fasciaMattina = new TimeInterval(LocalTime.of(8, 0), LocalTime.of(13, 0)); // 09:00 - 11:00
            chiamatePrenotazioneOraria.creaPrenotazioneOraria(new PrenotazioneServizioOrario("68090597defddd040b3fa6ee", "67fe64883a8df7041bc2bbdb", "Massaggio", "Mario", "Rossi", "15/04/2025", 2, 2, 100, fasciaMattina));
            */

            //chiamateServiziOrari.creaServizioOrario(new ServiziOrari("no fasce", "massaggio bellissimo", 100.00, null, 5));

        } catch (Exception e) {
            // Gestione di errori (es. server non raggiungibile, errori di rete)
            System.err.println("\nErrore durante l'esecuzione del client:");
            e.printStackTrace();
        }

        System.out.println("\n--- Client Terminato ---");
    }}
