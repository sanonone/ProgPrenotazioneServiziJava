package com.example.spring.spring.controller;

import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.model.persona.Utente;
import com.example.spring.spring.mongoHelper.UtenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.JsonNode;
import at.favre.lib.crypto.bcrypt.BCrypt;

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
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println("LOG: Chiamato endpoint POST /api/utenti/create");
        System.out.println("LOG: Ricevuto oggetto: " + utente.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Cliente ricevuto: " + utente.toString());
        Utente nuovoUtente = objectMapper.readValue(utente, Utente.class);

        //controllo non ci siano altri utenti nel database con lo stesso username
        List<Utente> utentiDB = utenteRepository.findAll().stream().filter(utDB -> utDB.getUsername().equals(nuovoUtente.getUsername())).toList();
        if(!utentiDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        //converto la stringa in nodo json per poi poter leggere il campo password e fare l'hash
        JsonNode rootNode = objectMapper.readTree(utente);

        // recupero la password
        String password = rootNode.get("password").asText();

        // faccio l'hash
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        nuovoUtente.setPassword(hashedPassword);

        utenteRepository.save(nuovoUtente);
        //gestoreClienti.creaCliente(nuovoUtente);

        return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtente(@PathVariable String id) {
        if (!utenteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utenteRepository.deleteById(id);
        return new ResponseEntity<>("Utente eliminato correttamente",HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Utente> login(@RequestBody String userAndPassword) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //converto la stringa in nodo json per poi poter leggere i campi user e password
        JsonNode rootNode = objectMapper.readTree(userAndPassword);

        System.out.println("recupero dati utente");
        // recupero l'utente
        String user = rootNode.get("username").asText();
        // recupero la password
        String password = rootNode.get("password").asText();


        //controllo password valida
        //BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        //boolean valid = result.verified;

        System.out.println("recupero dati utente dal db mongo");

        List<Utente> utentiDB = utenteRepository.findAll().stream().filter(utente -> utente.getUsername().equals(user)).toList();
        if(utentiDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utente utenteDB = utentiDB.getFirst();
        System.out.println("LOG: Utente trovato: " + utenteDB.toString());

        // Verifica
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), utenteDB.getPassword());

        // Controlli se la password è corretta
        if (result.verified) {
            System.out.println("Password corretta!");
            return new ResponseEntity<>(utenteDB,HttpStatus.OK);
        } else {
            System.out.println("Password sbagliata!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }



    }

    //magari fare update, ora non mi va
}
