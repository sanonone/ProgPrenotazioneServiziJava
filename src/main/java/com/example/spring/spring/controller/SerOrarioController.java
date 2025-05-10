package com.example.spring.spring.controller;

import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.model.servizio.ServiziOrari;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.example.spring.spring.mongoHelper.SerOrarioRepository;
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
@RequestMapping("/api/serviziOrari")
public class SerOrarioController {

    @Autowired
    private SerOrarioRepository serOrarioRepository;

    @GetMapping
    public ResponseEntity<List<ServiziOrari>> getAllServiziOrari() {
        return new ResponseEntity<>(serOrarioRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiziOrari> getServiziOrariById(@PathVariable String id) {
        System.out.println("LOG: Chiamato endpoint GET /api/serviziOrari/{id}");
        ServiziOrari servizio = serOrarioRepository.findById(id).orElse(null);
        if (servizio != null) {
            return new ResponseEntity<>(servizio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ServiziOrari> createServizioOrario(@RequestBody String servizio) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Configura l'ObjectMapper per gestire correttamente le date
        //altrimenti avevo errore durante la conversione in JSON
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println("LOG: Chiamato endpoint POST /api/serviziOrari/create");
        System.out.println("LOG: Ricevuto oggetto: " + servizio.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Servizio ricevuto: " + servizio.toString());

        try {
            ServiziOrari nuovoServizio = objectMapper.readValue(servizio, ServiziOrari.class);
            serOrarioRepository.save(nuovoServizio);
            //gestoreClienti.creaCliente(nuovoCliente);

            return new ResponseEntity<>(nuovoServizio, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            System.out.println("Errore durante la conversione dell'oggetto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServizioOrario(@PathVariable String id) {
        if (!serOrarioRepository.existsById(id)) {
            //return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        serOrarioRepository.deleteById(id);
        //return ResponseEntity.noContent().build(); // 204
        return new ResponseEntity<>("Servizio orario eliminato correttamente", HttpStatus.OK);

    }


}
