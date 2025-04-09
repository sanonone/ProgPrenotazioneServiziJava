package com.example.spring.spring.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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

        } catch (Exception e) {
            // Gestione di errori (es. server non raggiungibile, errori di rete)
            System.err.println("\nErrore durante l'esecuzione del client:");
            e.printStackTrace();
        }

        System.out.println("\n--- Client Java Semplice Terminato ---");
    }
}
