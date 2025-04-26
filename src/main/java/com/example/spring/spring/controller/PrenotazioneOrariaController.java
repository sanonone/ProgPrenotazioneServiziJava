package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizioOrario;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.model.servizio.ServiziOrari;
import com.example.spring.spring.mongoHelper.PrenotazioneOrariaRepository;
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
@RequestMapping("/api/prenotazioniServiziOrari")
public class PrenotazioneOrariaController {

    @Autowired
    private PrenotazioneOrariaRepository prenotazioneOrariaRepository;

    @Autowired
    private SerOrarioRepository serOrarioRepository;

    @GetMapping
    public ResponseEntity<List<PrenotazioneServizioOrario>> getAllPrenotazioniOrarie() {
        return new ResponseEntity<>(prenotazioneOrariaRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> creaPrenotazione(@RequestBody String prenotazione) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Configura l'ObjectMapper per gestire correttamente le date
        //altrimenti avevo errore durante la conversione in JSON
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            PrenotazioneServizioOrario prS=objectMapper.readValue(prenotazione, PrenotazioneServizioOrario.class);
            ServiziOrari servizio = serOrarioRepository.findById(prS.getIdServizio()).orElse(null);


            if (servizio.prenota(prS)) {
                prenotazioneOrariaRepository.save(prS);
                serOrarioRepository.save(servizio);
                return ResponseEntity.ok("Prenotazione effettuata con successo");

            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Servizio non disponibile");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
