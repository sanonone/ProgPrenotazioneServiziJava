package com.example.spring.spring.gestori;
import com.example.spring.spring.model.persona.Cliente;
import com.example.spring.spring.mongoHelper.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestoreClienti {
    @Autowired
    private ClienteRepository clienteRepository;

    public void eliminaClienteById(String id) {
        clienteRepository.deleteById(id); // Elimina un cliente per ID
    }

    public void eliminaCliente(Cliente cliente) {
        clienteRepository.delete(cliente); // Elimina un cliente specifico
    }

    public void eliminaClientiByNome(String nome) {
        List<Cliente> clientiDaEliminare = clienteRepository.findByNome(nome);
        clienteRepository.deleteAll(clientiDaEliminare); // Elimina tutti i clienti con un nome specifico
    }

    public void eliminaTuttiIClienti() {
        clienteRepository.deleteAll(); // Elimina tutti i clienti
    }
}
