package com.example.spring.spring.client;

import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.model.servizio.ServiziOrari;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ChiamateServiziOrari {

    private static final String SERVER_URL = "http://localhost:8080/api";

    // Creiamo un client HTTP standard (da Java 11 in poi)
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
            .build();

    // Create ObjectMapper instance



    ObjectMapper objectMapper = new ObjectMapper();



    public void creaServizioOrario(ServiziOrari nuovoServizioOrario){
        try{
            System.out.println("\nChiamata a POST " + SERVER_URL + "/serviziOrari/create");

            System.out.println("Invio questo object: "+nuovoServizioOrario);

            //ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            try {
                String serJson = objectMapper.writeValueAsString(nuovoServizioOrario);
                System.out.println("il servizio è: "+serJson);
                // Ora 'clienteJson' contiene la rappresentazione JSON dell'oggetto Cliente
                // che puoi inviare al server.


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL + "/serviziOrari/create")) // Sostituisci con l'URL del tuo endpoint
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(serJson))
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

    public void getServiziGiornalieriById(String id) {
        try {
            String url = SERVER_URL + "/serviziGiornalieri/" + id;
            System.out.println("\nChiamata a GET " + url);

            // Prepariamo la richiesta GET per /greet/Mondo
            HttpRequest reqId = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Inviamo la richiesta
            HttpResponse<String> resId = client.send(reqId, HttpResponse.BodyHandlers.ofString());
            System.out.println("Risposta dal server per get by id: " + resId.statusCode());
            System.out.println("Response Body: " + resId.body());

        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
        }
    }

    public List<Utente> getAllServiziGiornalieri() {
        try {
            System.out.println("\nChiamata a GET " + SERVER_URL + "/serviziGiornalieri");
            String url = SERVER_URL + "/serviziGiornalieri";
            System.out.println("\nChiamata a GET " + url);

            // Configurazione dell'ObjectMapper per gestire correttamente le date
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // opzionale

            HttpRequest requestGetUtenti = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/serviziGiornalieri"))
                    .GET() // Specifica il metodo GET
                    .build();

            // Inviamo la richiesta e otteniamo la risposta come Stringa
            // BodyHandlers.ofString() converte il corpo della risposta in una Stringa Java
            HttpResponse<String> responseGetServiziGiornalieri = client.send(requestGetUtenti, HttpResponse.BodyHandlers.ofString());

            // Controlliamo se la chiamata ha avuto successo (codice 2xx)
            if (responseGetServiziGiornalieri.statusCode() >= 200 && responseGetServiziGiornalieri.statusCode() < 300) {
                System.out.println("Risposta dal server (utenti): " + responseGetServiziGiornalieri.body());
                return objectMapper.readValue(responseGetServiziGiornalieri.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, ServiziGiornalieri.class));
            } else {
                System.err.println("Errore nella chiamata /utenti: Codice " + responseGetServiziGiornalieri.statusCode());
                return null;
            }



        } catch (Exception e) {
            System.out.println("Errore nelle chiamate utenti: " + e.getMessage());
            return null;
        }
    }

    public void deleteServizioGiornaliero(String id) {
        try {

            String url = SERVER_URL + "/serviziGiornalieri/" + id;
            System.out.println("\nChiamata a DELETE " + url);

            HttpRequest reqDelete = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/serviziGiornalieri/"+id))
                    .DELETE()
                    .build();

            HttpResponse<String> resDelete = client.send(reqDelete, HttpResponse.BodyHandlers.ofString());
            if (resDelete.statusCode() >= 200 && resDelete.statusCode() < 300) {
                System.out.println("Risposta dal server (delete): " + resDelete.statusCode());
            } else {
                System.err.println("Errore nella chiamata /serviziGiornalieri/delete: Codice " + resDelete.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
        }
    }
}
