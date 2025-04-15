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
    public ResponseEntity<Cliente> createCliente(@RequestBody String cliente) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("LOG: Chiamato endpoint POST /api/clienti/create");
        System.out.println("LOG: Ricevuto oggetto: " + cliente.toString()); // Stampa l'oggetto ricevuto

        System.out.println("Cliente ricevuto: " + cliente.toString());
        Cliente nuovoCliente = objectMapper.readValue(cliente, Cliente.class);
        clienteRepository.save(nuovoCliente);
        //gestoreClienti.creaCliente(nuovoCliente);

        return new ResponseEntity<>(nuovoCliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable String id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204

    }

}
