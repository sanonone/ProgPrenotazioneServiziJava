package com.example.spring.spring.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.spring.model.Messaggio;

@RestController // Dice a Spring che questa classe gestisce richieste web
@RequestMapping("/api") // Tutte le richieste inizieranno con /api
public class BasicController {

    // Endpoint 1: Risponde a GET /api/hello
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("LOG: Chiamato endpoint /api/hello");
        return "Ciao dal server semplice!"; // Restituisce una semplice stringa
    }

    // Endpoint 2: Risponde a GET /api/greet/NOME
    // Esempio: /api/greet/Mario -> restituisce "Ciao, Mario!"
    @GetMapping("/greet/{name}")
    public String greetByName(@PathVariable String name) {
        // @PathVariable prende il valore 'name' dall'URL
        System.out.println("LOG: Chiamato endpoint /api/greet/" + name);
        return "Ciao, " + name + "!"; // Restituisce una stringa personalizzata
    }

     // --- NUOVO ENDPOINT POST ---
    // Endpoint 3: Risponde a POST /api/send
    // Si aspetta un JSON nel corpo della richiesta che corrisponda alla classe Messaggio
    @PostMapping("/send")
    public String receiveMessage(@RequestBody Messaggio messaggio) {
        // @RequestBody: Dice a Spring di prendere il JSON dal corpo della richiesta
        // e convertirlo (deserializzarlo) in un oggetto Messaggio.
        // Spring usa Jackson per fare questo automaticamente.

        System.out.println("LOG: Chiamato endpoint POST /api/send");
        System.out.println("LOG: Ricevuto oggetto: " + messaggio.toString()); // Stampa l'oggetto ricevuto

        // Restituisce una conferma
        return "OK, Messaggio ricevuto dal server con ID: " + messaggio.getId() + " e testo: '" + messaggio.getTesto() + "'";
    }
}
