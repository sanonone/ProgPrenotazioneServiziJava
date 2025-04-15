package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.prenotazioneServizio.PrenotazioneServizio;
import com.example.spring.spring.model.servizio.ServiziGiornalieri;
import com.example.spring.spring.mongoHelper.PrenotazioneGiornalieraRepository;
import com.example.spring.spring.mongoHelper.SerGiornalieroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PrenotazioneGionalieraController {

    @Autowired
    private PrenotazioneGiornalieraRepository prenotazioneGiornalieraRepository;

    @Autowired
    private SerGiornalieroRepository serGiornalieroRepository;

    @PostMapping("/crea")
    public ResponseEntity<String> creaPrenotazione(@RequestBody String prenotazione) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PrenotazioneServizio prS=objectMapper.readValue(prenotazione, PrenotazioneServizio.class);
            ServiziGiornalieri servizio = serGiornalieroRepository.findById(prS.getIdServizio()).orElse(null);


            if (servizio.prenota(prS)) {
                prenotazioneGiornalieraRepository.save(prS);
                return ResponseEntity.ok("Prenotazione effettuata con successo");
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Servizio non disponibile");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
