package com.example.spring.spring.controller;

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
        Optional<Utente> utente = utenteRepository.findById(id);
        return utente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/create")
    public ResponseEntity<Utente> createUtente(@RequestBody String utente) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/utenti/create");
        System.out.println("LOG: Ricevuto oggetto: " + utente.toString()); // Stampa l'oggetto ricevuto
        Utente nuovoUtente = objectMapper.readValue(utente, Utente.class);
        utenteRepository.save(nuovoUtente);
        return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable String id) {
        Optional<Utente> utente = utenteRepository.findById(id);
        if (utente.isPresent()) {
            utenteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //magari fare update, ora non mi va
}
