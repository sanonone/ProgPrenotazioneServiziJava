package com.example.spring.spring.client;

import com.example.spring.spring.model.persona.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ChiamateServiziGiornalieri {

    private static final String SERVER_URL = "http://localhost:8080/api";

    // Creiamo un client HTTP standard (da Java 11 in poi)
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
            .build();

    // Create ObjectMapper instance

    ObjectMapper objectMapper = new ObjectMapper();

    public void creaUtente(){
        try{
            System.out.println("\nChiamata a POST " + SERVER_URL + "/utenti/create");
            Utente nuovoUtente = new Utente("Pippo", "Rossi", 23, 348578387,"pippo","pw","receptionist","02-02-2021");
            System.out.println("Invio questo object: "+nuovoUtente);

            //ObjectMapper objectMapper = new ObjectMapper();
            try {
                String utenteJson = objectMapper.writeValueAsString(nuovoUtente);
                System.out.println("l'utente è: "+utenteJson);
                // Ora 'clienteJson' contiene la rappresentazione JSON dell'oggetto Cliente
                // che puoi inviare al server.


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL + "/utenti/create")) // Sostituisci con l'URL del tuo endpoint
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(utenteJson))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch(Exception e){
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
        }
    }

    public void getUtenteById(String id) {
        try {
            String url = SERVER_URL + "/utenti/" + id;
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

    public List<Utente> getAllUtenti() {
        try {
            System.out.println("\nChiamata a GET " + SERVER_URL + "/utenti");
            String url = SERVER_URL + "/utenti";
            System.out.println("\nChiamata a GET " + url);

            HttpRequest requestGetUtenti = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/utenti"))
                    .GET() // Specifica il metodo GET
                    .build();

            // Inviamo la richiesta e otteniamo la risposta come Stringa
            // BodyHandlers.ofString() converte il corpo della risposta in una Stringa Java
            HttpResponse<String> responseGetUtenti = client.send(requestGetUtenti, HttpResponse.BodyHandlers.ofString());

            // Controlliamo se la chiamata ha avuto successo (codice 2xx)
            if (responseGetUtenti.statusCode() >= 200 && responseGetUtenti.statusCode() < 300) {
                System.out.println("Risposta dal server (utenti): " + responseGetUtenti.body());
                return objectMapper.readValue(responseGetUtenti.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Utente.class));
            } else {
                System.err.println("Errore nella chiamata /utenti: Codice " + responseGetUtenti.statusCode());
                return null;
            }



        } catch (Exception e) {
            System.out.println("Errore nelle chiamate utenti: " + e.getMessage());
            return null;
        }
    }

    public void deleteUtente(String id) {
        try {

            String url = SERVER_URL + "/utenti/" + id;
            System.out.println("\nChiamata a DELETE " + url);

            HttpRequest reqDelete = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/utenti/"+id))
                    .DELETE()
                    .build();

            HttpResponse<String> resDelete = client.send(reqDelete, HttpResponse.BodyHandlers.ofString());
            if (resDelete.statusCode() >= 200 && resDelete.statusCode() < 300) {
                System.out.println("Risposta dal server (delete): " + resDelete.body());
            } else {
                System.err.println("Errore nella chiamata /utenti/delete: Codice " + resDelete.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
        }
    }

}
