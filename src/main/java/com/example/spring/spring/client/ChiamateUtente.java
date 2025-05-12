package com.example.spring.spring.client;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiamateUtente {

    private static final String SERVER_URL = "http://localhost:8080/api";

    // Creiamo un client HTTP standard (da Java 11 in poi)
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
            .build();

    // Create ObjectMapper instance

    ObjectMapper objectMapper = new ObjectMapper();


    public Utente creaUtente(String nome, String cognome, int eta, long telefono, String username, String password, String ruolo, String dataAssunzione){
        try{
            System.out.println("\nChiamata a POST " + SERVER_URL + "/utenti/create");
            Utente nuovoUtente = new Utente(nome, cognome, eta, telefono,username,password,ruolo,dataAssunzione);
            System.out.println("Invio questo object: "+nuovoUtente);

            //ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
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
                return objectMapper.readValue(response.body(), Utente.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        catch(Exception e){
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
            return null;
        }
    }

    public void getUtenteById(String id) {
        try {
            String url = SERVER_URL + "/utenti/" + id;
            System.out.println("\nChiamata a GET " + url);

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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

    public Utente loginUtente(String username, String password) {
        try {
            String url = SERVER_URL + "/utenti/login";
            System.out.println("\nChiamata a POST " + url);

            Map<String, Object> data = new HashMap<>();
            data.put("username", username);
            data.put("password", password);

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            String oggettoJson = objectMapper.writeValueAsString(data);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/utenti/login")) // Sostituisci con l'URL del tuo endpoint
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(oggettoJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            //401 anauthorized, 200 ok, 404 not found
            if(response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Utente.class);
            } else if(response.statusCode() == 404) {
                System.out.println("Utente non trovato");
                return null;
            } else if(response.statusCode() == 401) {
                System.out.println("Credenziali errate");
                return null;
            } else {
                System.out.println("Errore sconosciuto");
                return null;
            }



        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
            return null;
        }
    }
}
