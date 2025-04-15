package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serviziGiornalieri")
public class SerGiornalieroController {

    @Autowired
    private SerGiornalieroRepository serGiornalieroRepository;

    @GetMapping
    public ResponseEntity<List<ServiziGiornalieri>> getAllServiziGiornalieri() {
        return new ResponseEntity<>(serGiornalieroRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiziGiornalieri> getServiziGiornalieriById(@PathVariable String id) {
        System.out.println("LOG: Chiamato endpoint GET /api/serviziGiornalieri/{id}");
        ServiziGiornalieri servizio = serGiornalieroRepository.findById(id).orElse(null);
        if (servizio != null) {
            return new ResponseEntity<>(servizio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ServiziGiornalieri> createServizioGiornaliero(@RequestBody String servizio) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/serviziGiornalieri/create");
        System.out.println("LOG: Ricevuto oggetto: " + servizio.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Servizio ricevuto: " + servizio.toString());
        ServiziGiornalieri nuovoServizio = objectMapper.readValue(servizio, ServiziGiornalieri.class);
        serGiornalieroRepository.save(nuovoServizio);
        //gestoreClienti.creaCliente(nuovoCliente);

        return new ResponseEntity<>(nuovoServizio, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServizioGiornaliero(@PathVariable String id) {
        if (!serGiornalieroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serGiornalieroRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204

    }




}
