package com.example.spring.spring.client;

import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ChiamatePrenotazioneOraria {

    private static final String SERVER_URL = "http://localhost:8080/api";

    // Creiamo un client HTTP standard (da Java 11 in poi)
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
            .build();

    // Create ObjectMapper instance



    ObjectMapper objectMapper = new ObjectMapper();



    public void creaPrenotazioneOraria(PrenotazioneServizioOrario nuovoPrenotazioneServizioOrario){
        try{
            System.out.println("\nChiamata a POST " + SERVER_URL + "/prenotazioniServiziOrari/create");

            System.out.println("Invio questo object: "+nuovoPrenotazioneServizioOrario);

            //ObjectMapper objectMapper = new ObjectMapper();
            // Configura l'ObjectMapper per gestire correttamente le date
            //altrimenti avevo errore durante la conversione in JSON
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            try {
                String prenotazioneSerJson = objectMapper.writeValueAsString(nuovoPrenotazioneServizioOrario);
                System.out.println("la prenotazione è: "+prenotazioneSerJson);
                // Ora 'clienteJson' contiene la rappresentazione JSON dell'oggetto Cliente
                // che puoi inviare al server.


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL + "/prenotazioniServiziOrari/create"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(prenotazioneSerJson))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch(Exception e){
            System.out.println("Errore nella creazione del servizio: " + e.getMessage());
        }
    }
}
