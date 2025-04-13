package com.example.spring.spring.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.example.spring.spring.model.Messaggio;
import com.example.spring.spring.model.persona.Cliente;
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

        try {
            // --- Chiamata 1: Endpoint /hello ---
            System.out.println("\nChiamata a GET " + SERVER_URL + "/hello");

            // Prepariamo la richiesta GET per /hello
            HttpRequest requestHello = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/hello"))
                    .GET() // Specifica il metodo GET
                    .build();

            // Inviamo la richiesta e otteniamo la risposta come Stringa
            // BodyHandlers.ofString() converte il corpo della risposta in una Stringa Java
            HttpResponse<String> responseHello = client.send(requestHello, HttpResponse.BodyHandlers.ofString());

            // Controlliamo se la chiamata ha avuto successo (codice 2xx)
            if (responseHello.statusCode() >= 200 && responseHello.statusCode() < 300) {
                System.out.println("Risposta dal server (hello): " + responseHello.body());
            } else {
                System.err.println("Errore nella chiamata /hello: Codice " + responseHello.statusCode());
            }


            // --- Chiamata 2: Endpoint /greet/{name} ---
            String nome = "Mondo"; // Il nome da passare nell'URL
            String urlGreet = SERVER_URL + "/greet/" + nome;
            System.out.println("\nChiamata a GET " + urlGreet);

            // Prepariamo la richiesta GET per /greet/Mondo
            HttpRequest requestGreet = HttpRequest.newBuilder()
                    .uri(URI.create(urlGreet))
                    .GET()
                    .build();

            // Inviamo la richiesta
            HttpResponse<String> responseGreet = client.send(requestGreet, HttpResponse.BodyHandlers.ofString());

             // Controlliamo la risposta
            if (responseGreet.statusCode() >= 200 && responseGreet.statusCode() < 300) {
                System.out.println("Risposta dal server (greet): " + responseGreet.body());
            } else {
                System.err.println("Errore nella chiamata /greet: Codice " + responseGreet.statusCode());
            }

/*
            // --- NUOVA Chiamata 3: Endpoint POST /send ---
            System.out.println("\nChiamata a POST " + SERVER_URL + "/send");
            
            // 1. Crea l'oggetto Java da inviare
            Messaggio mioMessaggio = new Messaggio(System.currentTimeMillis(), "Questo è il mio messaggio JSON!");
            
            // 2. Converti l'oggetto Java in una stringa JSON usando Jackson
            String jsonBody = objectMapper.writeValueAsString(mioMessaggio);
            System.out.println("   Invio questo JSON: " + jsonBody);
            
            // 3. Prepara la richiesta POST
            HttpRequest requestPost = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL + "/send"))
                    .header("Content-Type", "application/json") // FONDAMENTALE: indica al server che stiamo inviando JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) // Specifica il metodo POST e il corpo (la stringa JSON)
                    .build();
            
            // 4. Invia la richiesta POST
            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            
            // 5. Controlla la risposta
            if (responsePost.statusCode() >= 200 && responsePost.statusCode() < 300) {
                System.out.println("Risposta dal server (post): " + responsePost.body());
            } else {
                System.err.println("Errore nella chiamata POST /send: Codice " + responsePost.statusCode() + " Body: " + responsePost.body());
            }



 */


            // --- NUOVA Chiamata 4: Endpoint POST crea cliente/send-json ---
            System.out.println("\nChiamata a POST " + SERVER_URL + "/clienti/create");
            Cliente nuovoCliente = new Cliente("Mario", "Rossi", 23, 348578387,"mario.rossi@example.com");
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
            } catch (IOException e) {
                e.printStackTrace();
            }



            // --- Chiamata 5: Endpoint /clienti/{id} ---
            String id = "67fbb21307b3a35264c9587a"; // Il nome da passare nell'URL
            String url = SERVER_URL + "/clienti/" + id;
            System.out.println("\nChiamata a GET " + url);

            // Prepariamo la richiesta GET per /greet/Mondo
            HttpRequest reqId = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Inviamo la richiesta
            HttpResponse<String> resId = client.send(reqId, HttpResponse.BodyHandlers.ofString());

            // Controlliamo la risposta
            if (resId.statusCode() >= 200 && resId.statusCode() < 300) {
                System.out.println("Risposta dal server per get by id: " + resId.body());
            } else {
                System.err.println("Errore nella chiamata /clienti by id: Codice " + resId.statusCode());
            }


            // --- Chiamata 6: Endpoint /clienti ---
            System.out.println("\nChiamata a GET " + SERVER_URL + "/clienti");

            // Prepariamo la richiesta GET per /hello
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
            } else {
                System.err.println("Errore nella chiamata /clienti: Codice " + responseGetClienti.statusCode());
            }

        } catch (Exception e) {
            // Gestione di errori (es. server non raggiungibile, errori di rete)
            System.err.println("\nErrore durante l'esecuzione del client:");
            e.printStackTrace();
        }

        System.out.println("\n--- Client Java Semplice Terminato ---");
    }}
