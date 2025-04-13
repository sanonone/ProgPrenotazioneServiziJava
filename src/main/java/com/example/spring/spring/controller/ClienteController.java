package com.example.spring.spring.controller;
import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.mongoHelper.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    public ClienteController() {
        System.out.println("LOG: ClienteController inizializzato");
    }

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClienti() {
        return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String id) {
        System.out.println("LOG: Chiamato endpoint GET /api/clienti/{id}");
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Cliente> createCliente(@RequestBody String cliente) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/clienti/create");
        System.out.println("LOG: Ricevuto oggetto: " + cliente.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Cliente ricevuto: " + cliente.toString());
        //Cliente nuovoCliente = clienteRepository.save(cliente);
        Cliente nuovoCliente = objectMapper.readValue(cliente, Cliente.class);
        clienteRepository.save(nuovoCliente);
        return new ResponseEntity<>(nuovoCliente, HttpStatus.CREATED);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente clienteAggiornato) {
        Optional<Cliente> clienteEsistente = clienteRepository.findById(id);
        if (clienteEsistente.isPresent()) {
            clienteAggiornato.setId(id); // Assicurati che l'ID sia impostato
            Cliente clienteSalvato = clienteRepository.save(clienteAggiornato);
            return new ResponseEntity<>(clienteSalvato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */
}
