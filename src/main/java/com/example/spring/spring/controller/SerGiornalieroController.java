package com.example.spring.spring.controller;

import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    public ResponseEntity<ServiziGiornalieri> createServizioGiornaliero(@RequestBody String servizio) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Configura l'ObjectMapper per gestire correttamente le date
        //altrimenti avevo errore durante la conversione in JSON
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println("LOG: Chiamato endpoint POST /api/serviziGiornalieri/create");
        System.out.println("LOG: Ricevuto oggetto: " + servizio.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Servizio ricevuto: " + servizio.toString());

        try{
            ServiziGiornalieri nuovoServizio = objectMapper.readValue(servizio, ServiziGiornalieri.class);
            serGiornalieroRepository.save(nuovoServizio);
            //gestoreClienti.creaCliente(nuovoCliente);

            return new ResponseEntity<>(nuovoServizio, HttpStatus.CREATED);
        } catch(JsonProcessingException e){
            System.out.println("Errore durante la conversione dell'oggetto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServizioGiornaliero(@PathVariable String id) {
        if (!serGiornalieroRepository.existsById(id)) {
            //return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        serGiornalieroRepository.deleteById(id);
        //return ResponseEntity.noContent().build(); // 204
        return new ResponseEntity<>("Servizio giornaliero eliminato correttamente",HttpStatus.OK);

    }




}
