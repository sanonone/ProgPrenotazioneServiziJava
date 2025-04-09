package com.example.spring.spring.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
