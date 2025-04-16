package com.example.spring.spring.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.example.spring.spring.model.Messaggio;
import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SimpleJavaClient {

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

        try {

            /*
            chiamateCliente.creaCliente();
            chiamateCliente.getClienteById("67fbb21307b3a35264c9587a");
            chiamateCliente.getAllClienti();
            chiamateCliente.deleteCliente("67fbb21307b3a35264c9587a");
             */

            /*
            chiamateUtente.creaUtente();
            chiamateUtente.getAllUtenti();
            chiamateUtente.getUtenteById("67fe64883a8df7041bc2bbdb");
            chiamateUtente.deleteUtente("67fe64883a8df7041bc2bbdb");
             */

            /*
            chiamateServiziGiornalieri.creaServizioGiornaliero(new ServiziGiornalieri("test", "motorini da noleggiare per l'intero giorno o più", 20.00, 10));
            chiamateServiziGiornalieri.getAllServiziGiornalieri();
            chiamateServiziGiornalieri.getServiziGiornalieriById("67ff9f76d550d46d1e8ed1e4");
            chiamateServiziGiornalieri.deleteServizioGiornaliero("67ffa71ed550d46d1e8ed1e8");
             */

            chiamatePrenotazioneGiornaliera.creaPrenotazioneGiornaliera(new PrenotazioneServizio("67ffa52dd550d46d1e8ed1e5", "67fe41d2e1c5dc459f047a22", "Noleggio motorino", "Mario", "Rossi", "15/01/2026", 3, 1,10));


        } catch (Exception e) {
            // Gestione di errori (es. server non raggiungibile, errori di rete)
            System.err.println("\nErrore durante l'esecuzione del client:");
            e.printStackTrace();
        }

        System.out.println("\n--- Client Java Semplice Terminato ---");
    }}
