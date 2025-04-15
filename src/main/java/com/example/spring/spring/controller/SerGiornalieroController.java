package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serviziGiornalieri")
public class SerGiornalieroController {

    @GetMapping
    public ResponseEntity<List<ServiziGiornalieri>> getAllServiziGiornalieri(@PathVariable String utente) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Utente ut = objectMapper.readValue(utente, Utente.class);
            List<ServiziGiornalieri> servizi = ut.visualizzaServiziGiornalieriCreati();
            return new ResponseEntity<>(servizi, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiziGiornalieri> getServizioGiornalieroById(@PathVariable String id, @PathVariable String utente) {
        System.out.println("LOG: Chiamato endpoint GET /api/serviziGiornalieri/{id}");
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Utente ut = objectMapper.readValue(utente, Utente.class);
            ServiziGiornalieri servizio = ut.servizioGiornalieroById(id);
            return new ResponseEntity<>(servizio, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ServiziGiornalieri> createServizioGiornaliero(@RequestBody String servizio, @PathVariable String utente){
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/serviziGiornalieri/create");
        try{
            Utente ut = objectMapper.readValue(utente, Utente.class);
            ServiziGiornalieri nuovoServizio = objectMapper.readValue(servizio, ServiziGiornalieri.class);
            ut.creaServizioGiornaliero(nuovoServizio);
            return new ResponseEntity<>(nuovoServizio, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServizioGiornaliero(@PathVariable String id, @PathVariable String utente) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Utente ut = objectMapper.readValue(utente, Utente.class);
            ServiziGiornalieri servizioDaEliminare = ut.servizioGiornalieroById(id);
            if(servizioDaEliminare!=null){
                ut.eliminaServizioGiornaliero(id);
                return new ResponseEntity<>("Servizio eliminato con successo", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Servizio non trovato", HttpStatus.NOT_FOUND);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
