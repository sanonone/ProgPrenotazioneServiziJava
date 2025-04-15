package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.mongoHelper.UtenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping
    public ResponseEntity<List<Utente>> getAllUtenti() {
        return new ResponseEntity<>(utenteRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtenteById(@PathVariable String id) {
        System.out.println("LOG: Chiamato endpoint GET /api/utenti/{id}");
        Utente utente = utenteRepository.findById(id).orElse(null);
        if (utente != null) {
            return new ResponseEntity<>(utente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Utente> createUtente(@RequestBody String utente) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/utenti/create");
        System.out.println("LOG: Ricevuto oggetto: " + utente.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Cliente ricevuto: " + utente.toString());
        Utente nuovoUtente = objectMapper.readValue(utente, Utente.class);
        utenteRepository.save(nuovoUtente);
        //gestoreClienti.creaCliente(nuovoUtente);

        return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable String id) {
        if (!utenteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utenteRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204

    }

    //magari fare update, ora non mi va
}
