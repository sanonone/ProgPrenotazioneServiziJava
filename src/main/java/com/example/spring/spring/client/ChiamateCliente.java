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

public class ChiamateCliente {

    private static final String SERVER_URL = "http://localhost:8080/api";

    // Creiamo un client HTTP standard (da Java 11 in poi)
    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)) // Timeout di connessione
            .build();

    // Create ObjectMapper instance

    ObjectMapper objectMapper = new ObjectMapper();



    public Cliente creaCliente(String nome, String cognome, int eta, long telefono, String email, String password) {
        try{
            System.out.println("\nChiamata a POST " + SERVER_URL + "/clienti/create");
            Cliente nuovoCliente = new Cliente(nome, cognome, eta, telefono,email, password);
            System.out.println("Invio questo object: "+nuovoCliente);

            //ObjectMapper objectMapper = new ObjectMapper();
            try {
                String clienteJson = objectMapper.writeValueAsString(nuovoCliente);
                System.out.println("il cliente è: "+clienteJson);
                // Ora 'clienteJson' contiene la rappresentazione JSON dell'oggetto Cliente
                // che puoi inviare al server.


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL + "/clienti/create")) // Sostituisci con l'URL del tuo endpoint
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(clienteJson))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());
                return objectMapper.readValue(response.body(), Cliente.class);
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

    public void getClienteById(String id) {
        try {
            String url = SERVER_URL + "/clienti/" + id;
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

    public List<Cliente> getAllClienti() {
        try {
            System.out.println("\nChiamata a GET " + SERVER_URL + "/clienti");
            String url = SERVER_URL + "/clienti";
            System.out.println("\nChiamata a GET " + url);

            HttpRequest requestGetClienti = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/clienti"))
                    .GET() // Specifica il metodo GET
                    .build();

            // Inviamo la richiesta e otteniamo la risposta come Stringa
            // BodyHandlers.ofString() converte il corpo della risposta in una Stringa Java
            HttpResponse<String> responseGetClienti = client.send(requestGetClienti, HttpResponse.BodyHandlers.ofString());

            // Controlliamo se la chiamata ha avuto successo (codice 2xx)
            if (responseGetClienti.statusCode() >= 200 && responseGetClienti.statusCode() < 300) {
                System.out.println("Risposta dal server (clienti): " + responseGetClienti.body());
                return objectMapper.readValue(responseGetClienti.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Cliente.class));
            } else {
                System.err.println("Errore nella chiamata /clienti: Codice " + responseGetClienti.statusCode());
                return null;
            }



        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
            return null;
        }
    }

    public void deleteCliente(String id) {
        try {
            System.out.println("\nChiamata a DELETE " + SERVER_URL + "/clienti/" + id);
            String url = SERVER_URL + "/clienti/" + id;
            System.out.println("\nChiamata a DELETE " + url);

            HttpRequest reqDelete = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/clienti/"+id))
                    .DELETE()
                    .build();

            HttpResponse<String> resDelete = client.send(reqDelete, HttpResponse.BodyHandlers.ofString());
            if (resDelete.statusCode() >= 200 && resDelete.statusCode() < 300) {
                System.out.println("Risposta dal server (delete): " + resDelete.body());
            } else {
                System.err.println("Errore nella chiamata /clienti/delete: Codice " + resDelete.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Errore nella creazione del cliente: " + e.getMessage());
        }
    }

    public Cliente loginCliente(String mail, String password) {
        try {
            String url = SERVER_URL + "/clienti/login";
            System.out.println("\nChiamata a POST " + url);

            Map<String, Object> data = new HashMap<>();
            data.put("email", mail);
            data.put("password", password);

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            String oggettoJson = objectMapper.writeValueAsString(data);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/clienti/login")) // Sostituisci con l'URL del tuo endpoint
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(oggettoJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            //401 anauthorized, 200 ok, 404 not found
            if(response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Cliente.class);
            } else if(response.statusCode() == 404) {
                System.out.println("Cliente non trovato");
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
