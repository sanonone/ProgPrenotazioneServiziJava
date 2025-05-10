package com.example.spring.spring.controller;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.mongoHelper.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {



    @Autowired
    private ClienteRepository clienteRepository;


    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClienti() {
        return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String id) {
        System.out.println("LOG: Chiamato endpoint GET /api/clienti/{id}");
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /*
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

         */
    }

    @PostMapping("/create")
    public ResponseEntity<Cliente> createCliente(@RequestBody String cliente) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/clienti/create");
        System.out.println("LOG: Ricevuto oggetto: " + cliente.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Cliente ricevuto: " + cliente.toString());

        try{
            Cliente nuovoCliente = objectMapper.readValue(cliente, Cliente.class);

            /// /////////////////////////
            //controllo non ci siano altri utenti nel database con lo stesso username
            List<Cliente> clientiDB = clienteRepository.findAll().stream().filter(clDB -> clDB.getEmail().equals(nuovoCliente.getEmail())).toList();
            if(!clientiDB.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            String password = nuovoCliente.getPassword();

            // faccio l'hash
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            nuovoCliente.setPassword(hashedPassword);



            /// ///////////////////////
            clienteRepository.save(nuovoCliente);
            //gestoreClienti.creaCliente(nuovoCliente);

            return new ResponseEntity<>(nuovoCliente, HttpStatus.CREATED);
        } catch(JsonProcessingException e){
            System.out.println("LOG: Errore durante la conversione dell'oggetto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody String mailAndPassword) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try{
            //converto la stringa in nodo json per poi poter leggere i campi user e password
            JsonNode rootNode = objectMapper.readTree(mailAndPassword);

            System.out.println("recupero dati utente");
            // recupero l'utente
            String mail = rootNode.get("email").asText();
            // recupero la password
            String password = rootNode.get("password").asText();


            //controllo password valida
            //BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
            //boolean valid = result.verified;

            System.out.println("recupero dati cliente dal db mongo");

            List<Cliente> clientiDB = clienteRepository.findAll().stream().filter(cliente -> cliente.getEmail().equals(mail)).toList();
            if(clientiDB.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Cliente clienteDB = clientiDB.getFirst();
            System.out.println("LOG: Utente trovato: " + clienteDB.toString());

            // Verifica
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), clienteDB.getPassword());

            // Controlli se la password è corretta
            if (result.verified) {
                System.out.println("Password corretta!");
                return new ResponseEntity<>(clienteDB,HttpStatus.OK);
            } else {
                System.out.println("Password sbagliata!");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch(JsonProcessingException e){
            System.out.println("LOG: Errore durante la conversione dell'oggetto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }




    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable String id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return new ResponseEntity<>("Cliente eliminato correttamente",HttpStatus.OK);

    }

}
