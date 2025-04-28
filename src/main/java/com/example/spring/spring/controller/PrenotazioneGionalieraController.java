package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.PrenotazioneGiornalieraRepository;
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
@RequestMapping("/api/prenotazioniServiziGiornalieri")
public class PrenotazioneGionalieraController {

    @Autowired
    private PrenotazioneGiornalieraRepository prenotazioneGiornalieraRepository;

    @Autowired
    private SerGiornalieroRepository serGiornalieroRepository;

    @GetMapping
    public ResponseEntity<List<PrenotazioneServizio>> getAllPrenotazioni() {
        return new ResponseEntity<>(prenotazioneGiornalieraRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> creaPrenotazione(@RequestBody String prenotazione) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Configura l'ObjectMapper per gestire correttamente le date
        //altrimenti avevo errore durante la conversione in JSON
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            PrenotazioneServizio prS=objectMapper.readValue(prenotazione, PrenotazioneServizio.class);
            ServiziGiornalieri servizio = serGiornalieroRepository.findById(prS.getIdServizio()).orElse(null);


            if (servizio.prenota(prS)) {
                prenotazioneGiornalieraRepository.save(prS);
                serGiornalieroRepository.save(servizio);
                return ResponseEntity.ok("Prenotazione effettuata con successo");

            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Servizio non disponibile");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
